
/* Q2: increase salary of BADI Hatem by 5000DA*/

UPDATE employee SET salary = salary + 5000 WHERE first_name = 'BADI' AND last_name = 'Hatem';



/* Q3: Update date by adding 5 days for february interventions*/

UPDATE intervention SET start_date = start_date + interval '5 day' WHERE DATE_PART('month', start_date) = 02;



/*Q5: Display model with their marks*/

SELECT model.*, mark.mark FROM model INNER JOIN mark ON model.mark_id = mark.id;


/*Q6: display all vehicle who have at least one intervention */
SELECT * FROM vehicle WHERE id IN (SELECT intervention.vehicle_id FROM intervention);
