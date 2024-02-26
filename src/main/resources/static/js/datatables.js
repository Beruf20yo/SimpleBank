window.addEventListener('DOMContentLoaded', event => {
    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple, {
            perPage: 10,
            select: 10, type: "number",
            labels: {
                placeholder: "Поиск...",
                perPage: "{select} записей на страницу",
                noRows: "Здесь пока нет данных",
                noResults: "Ничего не найдено",
                info: "Показаны записи {start} - {end} из {rows}",
            }
      });
    }
});