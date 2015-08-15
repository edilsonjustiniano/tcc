MATCH (me:Person {email: 'pedro@gmail.com'}),  
(sp:Person), 
(service:Service), 
(executed:Execute), 
(partners:Person), 
(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners),
(sp)-[:PROVIDE]->(service), 
(service)-[:EXECUTE]->(executed), 
(sp)-[:EXECUTE]->(executed) 
WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' 
AND UPPER(service.name) = UPPER('doméstica') 
AND (executed)-[:TO]->(partners)
RETURN DISTINCT(sp.name), sp.email, /*partners.name,*/ service.name, count(executed) as total, avg(executed.note) as media, 1 as order ORDER BY order, media DESC
UNION ALL
MATCH (me:Person {email: 'pedro@gmail.com'}),  
(sp:Person),
(service:Service {name: 'Doméstica'}), 
(executed:Execute), 
(partners:Person),
(partners)-[:WORKS_IN]->(company)<-[:WORKS_IN]-(me),
(sp)-[:PROVIDE]->(service), 
(service)-[:EXECUTE]->(executed), 
(sp)-[:EXECUTE]->(executed),
(executed)-[:TO]->(partners)
WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' 
AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners))
RETURN DISTINCT(sp.name), sp.email, /*partners.name,*/ service.name, count(executed) as total, avg(executed.note) as media, 2 as order ORDER BY order, media DESC
UNION ALL
MATCH (me:Person {email: 'pedro@gmail.com'}),  
(sp:Person),  //remover o email para pegar todos os provedores de serviços 
(service:Service {name: 'Doméstica'}), 
(executed:Execute), 
(partners:Person),
(partners)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me),
(sp)-[:PROVIDE]->(service), 
(service)-[:EXECUTE]->(executed), 
(sp)-[:EXECUTE]->(executed),
(executed)-[:TO]->(partners)
WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' 
AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners))
AND NOT((partners)-[:WORKS_IN]->()<-[:WORKS_IN]-(me))
RETURN DISTINCT(sp.name), sp.email, /*partners.name,*/ service.name, count(executed) as total, avg(executed.note) as media, 3 as order ORDER BY order, media DESC
UNION ALL


/* Consulta para pegar todos da cidade minha que prestam tal serviço COLOCAR COMO UNION ALL */
MATCH (me:Person {email: 'pedro@gmail.com'}),  
(sp:Person),  
(service:Service {name: 'Doméstica'}),  
(partners:Person),
(partners)-[:LIVES_IN]->(city)<-[:LIVES_IN]-(me),
(sp)-[:PROVIDE]->(service)
WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' 
AND NOT((partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners))
AND NOT((partners)-[:WORKS_IN]->()<-[:WORKS_IN]-(me))
RETURN DISTINCT(sp.name), sp.email, /*partners.name,*/ service.name, 0 as total, 0 as media, 4 as order ORDER BY order, media DESC



