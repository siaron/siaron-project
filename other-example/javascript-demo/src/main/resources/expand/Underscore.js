load('https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.9.1/underscore-min.js');

var odds = _.filter([1, 2, 3, 4, 5, 6], function (num) {
    return num % 2 == 1;
});

print(odds);  // 1, 3, 5