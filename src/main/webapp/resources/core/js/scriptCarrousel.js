$(document).ready(function(){
    $('.carrousel-eventos').slick({
infinite: true,
speed: 400,
slidesToShow: 4,
slidesToScroll: 1,
responsive: [
{
  breakpoint: 1400,
  settings: {
    slidesToShow: 3,
    slidesToScroll: 1,
    infinite: true,
  }
},
{
  breakpoint: 1000,
  settings: {
    slidesToShow: 2,
    slidesToScroll: 1
  }
},
{
  breakpoint: 480,
  settings: {
    slidesToShow: 1,
    slidesToScroll: 1
  }
}
// You can unslick at a given breakpoint now by adding:
// settings: "unslick"
// instead of a settings object
]
});



    });
