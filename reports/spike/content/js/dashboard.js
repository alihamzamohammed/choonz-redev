/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 99.6202755210173, "KoPercent": 0.3797244789826916};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.7374942458186282, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.9714714714714715, 500, 1500, "Artist"], "isController": true}, {"data": [0.9595588235294118, 500, 1500, "Update Track"], "isController": false}, {"data": [0.9708588957055214, 500, 1500, "Read Genre"], "isController": false}, {"data": [0.9529616724738676, 500, 1500, "Delete Playlist"], "isController": false}, {"data": [0.6729249011857708, 500, 1500, "Album"], "isController": true}, {"data": [0.9491525423728814, 500, 1500, "Read Playlist"], "isController": false}, {"data": [0.3504775900073475, 500, 1500, "Read Album"], "isController": false}, {"data": [0.977810650887574, 500, 1500, "Read By Id Artists"], "isController": false}, {"data": [0.9616666666666667, 500, 1500, "Create Playlist"], "isController": false}, {"data": [0.9725609756097561, 500, 1500, "Create Genre"], "isController": false}, {"data": [0.9496527777777778, 500, 1500, "Playlist"], "isController": true}, {"data": [0.9657320872274143, 500, 1500, "Read By Id Genre"], "isController": false}, {"data": [0.9688427299703264, 500, 1500, "Update Artist"], "isController": false}, {"data": [0.9666666666666667, 500, 1500, "Update Genre"], "isController": false}, {"data": [0.8128019323671497, 500, 1500, "Create Artist"], "isController": false}, {"data": [0.6789264413518886, 500, 1500, "Delete Album"], "isController": false}, {"data": [0.9570446735395189, 500, 1500, "Update Playlist"], "isController": false}, {"data": [0.9512635379061372, 500, 1500, "Read By Id Track"], "isController": false}, {"data": [0.942090395480226, 500, 1500, "Read Artists"], "isController": false}, {"data": [0.9520295202952029, 500, 1500, "Delete Track"], "isController": false}, {"data": [0.9645390070921985, 500, 1500, "Create Track"], "isController": false}, {"data": [0.9515050167224081, 500, 1500, "Read By Id Playlist"], "isController": false}, {"data": [0.9589285714285715, 500, 1500, "Read Track"], "isController": false}, {"data": [0.462298682284041, 500, 1500, "Create Album"], "isController": false}, {"data": [0.952922077922078, 500, 1500, "Delete Genre"], "isController": false}, {"data": [0.9471153846153846, 500, 1500, "Genre"], "isController": true}, {"data": [0.972972972972973, 500, 1500, "Delete Artist"], "isController": false}, {"data": [0.33948497854077253, 500, 1500, "Read By Id Album"], "isController": false}, {"data": [0.9501845018450185, 500, 1500, "Track"], "isController": true}, {"data": [0.5049928673323824, 500, 1500, "Update Album"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 11324, 43, 0.3797244789826916, 4723.302719886967, 2, 49644, 10.5, 21369.5, 29903.75, 40489.0, 72.47916639997952, 421.32218254361936, 19.26219808681307], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Artist", 333, 0, 0.0, 598.7297297297298, 23, 46076, 47.0, 88.0, 119.40000000000009, 30100.020000000044, 2.5597072863259362, 6.471076821004974, 3.3996112396516343], "isController": true}, {"data": ["Update Track", 272, 0, 0.0, 474.50735294117646, 3, 29448, 7.0, 20.700000000000017, 54.19999999999959, 19043.369999999923, 2.1621621621621623, 0.7896959459459459, 0.6904560810810811], "isController": false}, {"data": ["Read Genre", 326, 0, 0.0, 272.2423312883435, 4, 26348, 10.0, 27.0, 43.64999999999998, 10281.550000000021, 2.840191320863209, 4.247558938326029, 0.6407072217962902], "isController": false}, {"data": ["Delete Playlist", 287, 0, 0.0, 701.0487804878051, 4, 35035, 8.0, 24.19999999999999, 512.3999999999762, 29934.080000000024, 2.1019943312069254, 0.39207120826222924, 0.5419204135142854], "isController": false}, {"data": ["Album", 506, 4, 0.7905138339920948, 10244.962450592884, 23, 60400, 59.0, 45175.80000000001, 54469.1, 59238.22, 3.359938378995737, 28.62074239033719, 4.404841708140214], "isController": true}, {"data": ["Read Playlist", 295, 1, 0.3389830508474576, 813.8610169491526, 5, 44193, 11.0, 33.400000000000034, 1048.3999999999921, 33408.88000000016, 2.1525619135180887, 3.936584534116282, 0.49189403101878204], "isController": false}, {"data": ["Read Album", 1361, 13, 0.9551800146950772, 12056.038941954452, 5, 48142, 6339.0, 31701.399999999998, 39488.599999999984, 44892.99999999995, 8.770686188585863, 390.2001044378641, 1.978543466370444], "isController": false}, {"data": ["Read By Id Artists", 338, 0, 0.0, 135.3639053254438, 2, 15624, 4.0, 16.0, 25.0, 5572.330000000018, 3.029651141945431, 0.9763524187910079, 0.7869992224194186], "isController": false}, {"data": ["Create Playlist", 300, 0, 0.0, 475.42, 2, 29631, 4.0, 13.0, 469.6999999999963, 24632.19000000005, 2.19785050220884, 0.7834135090881117, 0.7533647717532252], "isController": false}, {"data": ["Create Genre", 328, 0, 0.0, 325.8475609756098, 2, 42934, 4.0, 12.0, 29.55000000000001, 5137.819999999999, 2.418593676262388, 0.8054105894584709, 0.7628962474929211], "isController": false}, {"data": ["Playlist", 288, 0, 0.0, 1446.291666666667, 20, 60327, 41.0, 80.0, 1313.5000000000105, 54813.82000000005, 2.109132985228746, 6.3788986119451625, 2.9455429003507896], "isController": true}, {"data": ["Read By Id Genre", 321, 0, 0.0, 188.95015576323988, 2, 14930, 4.0, 14.0, 334.3999999999935, 6657.8999999999805, 3.132257372026307, 1.0369484854657403, 0.7249462862990574], "isController": false}, {"data": ["Update Artist", 337, 0, 0.0, 392.56973293768544, 4, 24067, 7.0, 20.0, 42.89999999999969, 20603.88000000004, 2.735101003952505, 0.8734160432543644, 0.7639051632132974], "isController": false}, {"data": ["Update Genre", 315, 0, 0.0, 137.7238095238095, 3, 7824, 6.0, 20.400000000000034, 296.79999999999893, 4852.199999999975, 3.441306604031245, 1.0922115686622604, 1.0552444078767684], "isController": false}, {"data": ["Create Artist", 414, 2, 0.4830917874396135, 4954.632850241547, 2, 47883, 5.0, 29352.0, 34734.5, 43065.95000000005, 2.7587126007862994, 1.0911314303325115, 0.7704998084227361], "isController": false}, {"data": ["Delete Album", 503, 4, 0.7952286282306164, 6754.616302186879, 4, 46850, 11.0, 29587.2, 33896.6, 40339.439999999995, 3.3433923135211305, 1.0113224283962352, 0.8521732361611475], "isController": false}, {"data": ["Update Playlist", 291, 1, 0.3436426116838488, 774.5498281786943, 3, 40794, 6.0, 20.0, 528.5999999999959, 29422.79999999999, 2.1484894125985647, 0.8368166686970262, 0.7175618936608488], "isController": false}, {"data": ["Read By Id Track", 277, 0, 0.0, 682.5415162454873, 2, 34831, 5.0, 18.0, 1106.0999999999922, 27302.079999999998, 2.0890839706170716, 0.7813663679163462, 0.483508692418209], "isController": false}, {"data": ["Read Artists", 354, 0, 0.0, 1388.2994350282488, 6, 41111, 16.0, 50.0, 2496.0, 37452.14999999997, 2.401432719181613, 3.529886686633381, 0.6097387763547065], "isController": false}, {"data": ["Delete Track", 271, 0, 0.0, 910.9483394833949, 4, 36212, 8.0, 26.80000000000001, 913.5999999999968, 33859.71999999998, 2.0596304825311416, 0.38416935758149223, 0.5249644100982694], "isController": false}, {"data": ["Create Track", 282, 0, 0.0, 432.04609929078015, 2, 27769, 4.0, 16.700000000000017, 195.64999999999492, 19507.63000000013, 2.158058665523865, 0.811379478737, 0.6975756037972649], "isController": false}, {"data": ["Read By Id Playlist", 299, 0, 0.0, 579.3979933110369, 2, 29932, 4.0, 17.0, 600.0, 25934.0, 2.174023688860128, 0.7706744131408463, 0.5095368020765926], "isController": false}, {"data": ["Read Track", 280, 0, 0.0, 676.3678571428571, 3, 38911, 9.0, 32.80000000000001, 319.8999999999977, 28531.219999999983, 2.150719338807426, 4.590306568719324, 0.4851720383442534], "isController": false}, {"data": ["Create Album", 1366, 9, 0.6588579795021962, 4255.136163982431, 2, 44188, 1476.0, 10712.5, 16046.399999999992, 32623.629999999997, 8.885882115698609, 3.9107222137295334, 2.655351491605248], "isController": false}, {"data": ["Delete Genre", 308, 0, 0.0, 299.56818181818176, 4, 20273, 7.0, 25.0, 815.8500000000008, 11996.13000000008, 2.75467310616224, 0.5138110969501833, 0.7021188288167427], "isController": false}, {"data": ["Genre", 312, 0, 0.0, 449.81730769230774, 18, 31339, 37.0, 77.0, 1078.749999999993, 17611.080000000034, 2.789624741825595, 7.370212178008458, 3.71220008181111], "isController": true}, {"data": ["Delete Artist", 333, 0, 0.0, 409.87687687687685, 4, 36596, 8.0, 23.600000000000023, 34.80000000000007, 19251.460000000003, 2.5610065601759637, 0.47768774706407136, 0.6552575378575219], "isController": false}, {"data": ["Read By Id Album", 1165, 8, 0.6866952789699571, 13263.241201716743, 2, 49261, 9027.0, 32490.000000000007, 35599.80000000002, 43351.119999999995, 7.573639832794836, 3.338392076445005, 1.7528834378636484], "isController": false}, {"data": ["Track", 271, 0, 0.0, 1432.8081180811807, 19, 60785, 40.0, 88.80000000000001, 1153.399999999995, 55039.639999999905, 2.0596617898536955, 7.0974996437393125, 2.7897958032490977], "isController": true}, {"data": ["Update Album", 701, 5, 0.7132667617689016, 10426.154065620545, 3, 49644, 773.0, 32430.200000000004, 37673.8, 47003.32000000001, 4.587246016425089, 2.0309483689428394, 1.384237323315774], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["500", 43, 100.0, 0.3797244789826916], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 11324, 43, "500", 43, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Read Playlist", 295, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Read Album", 1361, 13, "500", 13, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Create Artist", 414, 2, "500", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Delete Album", 503, 4, "500", 4, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Update Playlist", 291, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Create Album", 1366, 9, "500", 9, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Read By Id Album", 1165, 8, "500", 8, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["Update Album", 701, 5, "500", 5, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
