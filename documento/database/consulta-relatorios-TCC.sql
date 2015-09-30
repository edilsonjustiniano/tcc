MATCH (me:Person {email: 'edilsonjustiniano@gmail.com'}), 
(sp:Person {email: 'mariaaparecida@gmail.com'}),  
(service:Service {name: 'Doméstica'}), 
(executed:Execute), 
(partners:Person), 
(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners),  
(sp)-[:PROVIDE]->(service),  
(service)-[:EXECUTE]->(executed), 
(sp)-[:EXECUTE]->(executed), 
(executed)-[:TO]->(partners) 
WHERE sp.typeOfAccount <> 'CONTRACTOR' AND partners.typeOfAccount <> 'SERVICE_PROVIDER' 
AND UPPER(service.name) = UPPER('Doméstica') 
RETURN DISTINCT({partner: partners.name, note: executed.note, comments: executed.comments, 
	date: executed.date}) as execution ORDER BY execution.note DESC
	LIMIT 3;





MATCH (me:Person {email: 'edilsonjustiniano@gmail.com'}), 
(sp:Person {email: 'mariaaparecida@gmail.com'}), (otherSP:Person), 
(service:Service {name: 'Doméstica'}), 
(executed:Execute), 
(partners:Person), 
(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners),  
(otherSP)-[:PROVIDE]->(service),  
(service)-[:EXECUTE]->(executed), 
(otherSP)-[:EXECUTE]->(executed), 
(executed)-[:TO]->(partners) 
WHERE otherSP.typeOfAccount <> 'CONTRACTOR' 
AND partners.typeOfAccount <> 'SERVICE_PROVIDER' 
AND UPPER(service.name) = UPPER('Doméstica') 
AND sp <> otherSP
RETURN DISTINCT({partner: partners.name, note: executed.note, comments: executed.comments, 
	date: executed.date, to: otherSP.name}) as execution ORDER BY execution.note DESC
	LIMIT 3;
