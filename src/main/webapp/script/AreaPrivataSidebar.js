$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $('#overlay').toggleClass('active');
    });

    $('#dismiss, #overlay').on('click', function () {
        $('#overlay').removeClass('active');
        $('#sidebar').removeClass('active');
    });

    $('#gestioneUtenti').on('click', function () {
        $('.active').removeClass('active');
        $('#gestioneUtenti').toggleClass('active');
    });
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
});