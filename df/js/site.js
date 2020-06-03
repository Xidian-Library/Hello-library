// call this from the developer console and you can control both instances
var calendars = {};

$(document).ready( function() {

  // assuming you've got the appropriate language files,
  // clndr will respect whatever moment's language is set to.
  // moment.lang('ru');

  // here's some magic to make sure the dates are happening this month.
  var thisMonth = moment().format('YYYY-MM');

  var eventArray = [
    { startDate: thisMonth + '-10', endDate: thisMonth + '-14', title: 'Multi-Day Event' },
    { startDate: thisMonth + '-23', endDate: thisMonth + '-26', title: 'Another Multi-Day Event' }
  ];

  // the order of the click handlers is predictable.
  // direct click action callbacks come first: click, nextMonth, previousMonth, nextYear, previousYear, or today.
  // then onMonthChange (if the month changed).
  // finally onYearChange (if the year changed).

  calendars.clndr1 = $('.cal1').clndr({
    events: eventArray,
    // constraints: {
    //   startDate: '2013-11-01',
    //   endDate: '2013-11-15'
    // },
    clickEvents: {
      click: function(target) {
        if($(target.element).hasClass('last-month') || $(target.element).hasClass('next-month')) {
          console.log('not a valid datepicker date.');
        } else {
          var date = target.date._i.split('-');
          var whatDay = new Date(target.date._i);
          var whatTime = whatDay.getTime();//点击的那个日期的时间戳
          var StartTime;//开始时间的时间戳
          var EndTime;//结束时间的时间戳（左闭右开）
          var dayFineMoney;//日罚金收入
          var monthFineMoney;//月罚金收入
          var weekFineMoney;//周罚金收入
          var daySeMoney;//日保证金收入
          var monthSeMoney;//月保证金收入
          var weekSeMoney;//周保证金收入
          var jsonMoney;
          var xml1 = new XMLHttpRequest();
          var xml2 = new XMLHttpRequest();
          var xml3 = new XMLHttpRequest();
          var nUrl  =  'http://114.55.250.159:8080/api/getIncome?';//newURL先放在这里，之后填写一下就好
          if(date[2] === '01') {//当日期是当月第一天
            if(whatDay.getDay() == 1) {//当日期是该月第一天且为周一时，计算周收入、月收入和当日收入
              console.log('Sunday and firstday');
              console.log(whatDay.getDay());
              
              StartTime = whatTime;
              EndTime = whatTime + 7*24*60*60*1000;
              var nUrl1 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              xml1.open('GET', nUrl1);
              xml1.onreadystatechange = function() {//周收入
                if (xml1.readyState === 4 && xml1.status===200) {
                  jsonMoney = JSON.parse(xml1.responseText);
                  weekFineMoney = jsonMoney.fine;
                  weekSeMoney = jsonMoney.security;
                  alert("Week Fine：" + weekFineMoney + "  Week Security: " + weekSeMoney);
								} 
              };
              xml1.send();

              StartTime = whatTime;
              EndTime = whatTime + 1*24*60*60*1000;
              var nUrl2 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              xml2.open('GET', nUrl2);
              xml2.onreadystatechange = function() {//日收入
                if (xml2.readyState === 4 && xml2.status===200) {
                  jsonMoney = JSON.parse(xml2.responseText);
                  dayFineMoney = jsonMoney.fine;
                  daySeMoney = jsonMoney.security;
                  alert("Day Fine：" + dayFineMoney + "  Day Security: " + daySeMoney);
								} 
              };
              xml2.send();

              var Year = whatDay.getFullYear();
              var Month = whatDay.getMonth();
              if(Month == 11) {
                Year += 1;
                Month = 0;
              }
              StartTime = whatTime;
              EndTime = +new Date(Year, Month, 1);
              var nUrl3 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              xml3.open('GET', nUrl3);
              xml3.onreadystatechange = function() {//月收入
                if (xml3.readyState === 4 && xml3.status===200) {
                  jsonMoney = JSON.parse(xml3.responseText);
                  monthFineMoney = jsonMoney.fine;
                  monthSeMoney = jsonMoney.security;
                  alert("Month Fine：" + monthFineMoney + "  Month Security: " + monthSeMoney);
								} 
              };
              xml3.send();

            } else {//当日期是该月第一天但不为周一时，计算月收入和当日收入
              console.log('firstday');
              console.log(whatDay.getDay());
              console.log(whatTime);

              StartTime = whatTime;
              EndTime = whatTime + 1*24*60*60*1000;
              var nUrl2 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              xml2.open('GET', nUrl2);
              xml2.onreadystatechange = function() {//日收入
                if (xml2.readyState === 4 && xml2.status===200) {
                  console.log(nUrl2);
                  console.log(xml2.responseText);
                  jsonMoney = JSON.parse(xml2.responseText);
                  dayFineMoney = jsonMoney.fine;
                  daySeMoney = jsonMoney.security;
                  alert("Day Fine：" + dayFineMoney + "  Day Security: " + daySeMoney);
								} 
              };
              xml2.send();
              var Year = whatDay.getFullYear();
              var Month = whatDay.getMonth();
              if(Month == 11) {
                Year += 1;
                Month = 0;
              }
              StartTime = whatTime;
              EndTime = +new Date(Year, Month+1, 1);
              var nUrl3 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              console.log(nUrl3);
              xml3.open('GET', nUrl3);
              xml3.onreadystatechange = function() {//月收入
                if (xml3.readyState === 4 && xml3.status===200) {
                  console.log(xml3.responseText);
                  jsonMoney = JSON.parse(xml3.responseText);
                  monthFineMoney = jsonMoney.fine;
                  monthSeMoney = jsonMoney.security;
                  alert("Month Fine：" + monthFineMoney + "  Month Security: " + monthSeMoney);
								} 
              };
              xml3.send();
            }
          } else if(whatDay.getDay() == 1) {//当日期不是当月第一天，却是周一时，计算周收入和当日收入
            console.log('Sunday');
            console.log(whatDay.getDay());

            StartTime = whatTime;
              EndTime = whatTime + 7*24*60*60*1000;
              var nUrl1 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              xml1.open('GET', nUrl1);
              xml1.onreadystatechange = function() {//周收入
                if (xml1.readyState === 4 && xml1.status===200) {
                  jsonMoney = JSON.parse(xml1.responseText);
                  weekFineMoney = jsonMoney.fine;
                  weekSeMoney = jsonMoney.security;
                  alert("Week Fine：" + weekFineMoney + "  Week Security: " + weekSeMoney);
								} 
              };
              xml1.send();

              StartTime = whatTime;
              EndTime = whatTime + 1*24*60*60*1000;
              var nUrl2 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              xml2.open('GET', nUrl2);
              xml2.onreadystatechange = function() {//日收入
                if (xml2.readyState === 4 && xml2.status===200) {
                  jsonMoney = JSON.parse(xml2.responseText);
                  dayFineMoney = jsonMoney.fine;
                  daySeMoney = jsonMoney.security;
                  alert( "Day Fine：" + dayFineMoney + "  Day Security: " + daySeMoney);
								}
              };
              xml2.send();


          } else {//当日期是普通的一天时，计算当日收入
            console.log('Normalday');
            console.log(whatDay.getDay());

              var xml2= new XMLHttpRequest();
              StartTime = whatTime;
              EndTime = whatTime + 1*24*60*60*1000;
              var nUrl2 = nUrl + 'start=' + StartTime + '&end=' + EndTime;
              console.log(nUrl2);
              xml2.open('GET', nUrl2);
              xml2.onreadystatechange = function() {//日收入
                if (xml2.readyState === 4 && xml2.status===200) {
                  console.log(xml2.responseText);
                  jsonMoney = JSON.parse(xml2.responseText);
                  dayFineMoney = jsonMoney.fine;
                  daySeMoney = jsonMoney.security;
                  alert("Day Fine：" + dayFineMoney + "  Day Security: " + daySeMoney + "\n");
                }
              };
              xml2.send();
          }
        }
      },
      nextMonth: function() {
        console.log('next month.');
      },
      previousMonth: function() {
        console.log('previous month.');
      },
      onMonthChange: function() {
        console.log('month changed.');
      },
      nextYear: function() {
        console.log('next year.');
      },
      previousYear: function() {
        console.log('previous year.');
      },
      onYearChange: function() {
        console.log('year changed.');
      }
    },
    multiDayEvents: {
      startDate: 'startDate',
      endDate: 'endDate'
    },
    showAdjacentMonths: true,
    adjacentDaysChangeMonth: false
  });

  // calendars.clndr2 = $('.cal2').clndr({
  //   template: $('#template-calendar').html(),
  //   events: eventArray,
  //   startWithMonth: moment().add('month', 1),
  //   clickEvents: {
  //     click: function(target) {
  //       console.log(target);
  //     }
  //   }
  // });

  // bind both clndrs to the left and right arrow keys
  $(document).keydown( function(e) {
    if(e.keyCode == 37) {
      // left arrow
      calendars.clndr1.back();
      calendars.clndr2.back();
    }
    if(e.keyCode == 39) {
      // right arrow
      calendars.clndr1.forward();
      calendars.clndr2.forward();
    }
  });

});