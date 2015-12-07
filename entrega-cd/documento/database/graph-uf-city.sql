CREATE 
(mgUF:UF {id: 1, abbreviation: "MG", name: "Minas Gerais"}),
(spUF:UF {id: 2, abbreviation: "SP", name: "São Paulo"}),
(staRitaCity:City {id: 1, name: "Santa Rita"}),
(pousoAlegreCity:City {id: 2, name: "Pouso Alegre"}),
(itajubaCity:City {id: 3, name: "Itajubá"}),
(spCity:City {id: 4, name: "São Paulo"}),
(staRitaCity)-[:BELONGS_TO]->(mgUF),
(pousoAlegreCity)-[:BELONGS_TO]->(mgUF),
(itajubaCity)-[:BELONGS_TO]->(mgUF),
(spCity)-[:BELONGS_TO]->(spUF)
