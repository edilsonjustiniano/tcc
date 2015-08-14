MATCH (me:Person {email: 'robertorocha@gmail.com'}),
(partners:Person), 
(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners),
(partners)-[partnerA:PARTNER_OF]->(user)-[partnerB:PARTNER_OF]->(partners)
WHERE partners <> me
RETURN DISTINCT(partners), partners.name, user, user.name, partnerA.since, partnerB.since
ORDER BY partnerA.since, partnerB.since DESC


/* Funcionou a ordenação pela data */
MATCH (me:Person {email: 'robertorocha@gmail.com'}),
(partners:Person), 
(sp:Person),
(service:Service),
(executed:Execute),
(sp)-[:PROVIDE]->(service),
(service)-[:EXECUTE]->(executed),
(executed)-[:TO]->(partners),
(partners)-[:PARTNER_OF]->(me)-[:PARTNER_OF]->(partners)/*,
(partners)-[partnerA:PARTNER_OF]->(user)-[partnerB:PARTNER_OF]->(partners)*/
WHERE partners <> me AND sp.typeOfAccount <> 'CONTRACTOR'
RETURN DISTINCT(executed), executed.note, partners.name, executed.comments, executed.date
ORDER BY executed.date DESC;