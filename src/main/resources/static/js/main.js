$(function() {

var dates = $.fn.datepicker.dates = {
en: {
days: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
daysShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
daysMin: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
monthsShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
today: "Today",
clear: "Clear"
},
cn: {
days: ["週日", "週一", "週二", "週三", "週四", "週五", "週六", "週日"],
daysShort: ["日", "一", "二", "三", "四", "五", "六", "七"],
daysMin: ["日", "一", "二", "三", "四", "五", "六", "七"],
months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
today: "今天",
clear: "清除"
}
};

var defaults = $.fn.datepicker.defaults = {
autoclose: false,
beforeShowDay: $.noop,
calendarWeeks: false,
clearBtn: false,
daysOfWeekDisabled: [],
endDate: Infinity,
forceParse: true,
format: 'mm/dd/yyyy',
keyboardNavigation: true,
language: 'cn',
minViewMode: 0,
orientation: "auto",
rtl: false,
startDate: -Infinity,
startView: 0,
todayBtn: false,
todayHighlight: false,
weekStart: 0
};

  var day = ['Apple', 'Banana'];

  $('#start').pickadate({
 	
	monthsFull: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
  	weekdaysShort: ["週日", "週一", "週二", "週三", "週四", "週五", "週六", "週日"],
	monthsTitle: "月份",
	weekStart: 0,
	today: "今天",
	clear: "清除",
	close: '關閉',
	minViewMode: 0,
	orientation: "auto",
 	minView: "month",
 	changeMonth:true,
 	format:'yyyy-mm-dd'
  })
    $('#end').pickadate({
    monthsFull: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
  	weekdaysShort: ["週日", "週一", "週二", "週三", "週四", "週五", "週六", "週日"],
	monthsTitle: "月份",
	weekStart: 0,
	today: "今天",
	clear: "清除",
	close: '關閉',
  	minView: "month",
 	format:'yyyy-mm-dd'
  })
	

  var from_$input = $('#start').pickadate(),
    from_picker = from_$input.pickadate('picker')

  var to_$input = $('#end').pickadate(),
    to_picker = to_$input.pickadate('picker')


  // Check if there’s a “from” or “to” date to start with.
  if ( from_picker.get('value') ) {
    to_picker.set('min', from_picker.get('select'))
  }
  if ( to_picker.get('value') ) {
    from_picker.set('max', to_picker.get('select'))
  }

  // When something is selected, update the “from” and “to” limits.
  from_picker.on('set', function(event) {
    if ( event.select ) {
      to_picker.set('min', from_picker.get('select'))    
    }
    else if ( 'clear' in event ) {
      to_picker.set('min', false)
    }
  })
  to_picker.on('set', function(event) {
    if ( event.select ) {
      from_picker.set('max', to_picker.get('select'))
    }
    else if ( 'clear' in event ) {
      from_picker.set('max', false)
    }
  })

});