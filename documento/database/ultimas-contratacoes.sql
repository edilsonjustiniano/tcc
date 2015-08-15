/* Funcionando a consulta para a busca de suas últimas avaliações */
MATCH (me:Person {email: 'edilsonjustiniano@gmail.com'}),
(sp:Person),
(service:Service),
(executed:Execute),
(sp)-[:PROVIDE]->(service),
(service)-[:EXECUTE]->(executed),
(sp)-[:EXECUTE]->(executed),
(executed)-[:TO]->(me)
WHERE me.typeOfAccount <> 'SERVICE_PROVIDER' 
AND sp.typeOfAccount <> 'CONTRACTOR'
RETURN sp, sp.name, executed, executed.note, executed.comments, executed.date
ORDER BY executed.date ASC
LIMIT 4;