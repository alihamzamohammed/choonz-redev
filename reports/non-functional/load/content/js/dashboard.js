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

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3360043199388887, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.1763373682155408, 500, 1500, "Artist"], "isController": true}, {"data": [0.35341792877609496, 500, 1500, "Update Track"], "isController": false}, {"data": [0.36971554298286974, 500, 1500, "Read Genre"], "isController": false}, {"data": [0.3682830401807326, 500, 1500, "Delete Playlist"], "isController": false}, {"data": [0.18117003237243717, 500, 1500, "Album"], "isController": true}, {"data": [0.35577153827693786, 500, 1500, "Read Playlist"], "isController": false}, {"data": [0.35161808583888, 500, 1500, "Read Album"], "isController": false}, {"data": [0.3550972762645914, 500, 1500, "Read By Id Artists"], "isController": false}, {"data": [0.3918037999841005, 500, 1500, "Create Playlist"], "isController": false}, {"data": [0.3978368210674818, 500, 1500, "Create Genre"], "isController": false}, {"data": [0.16773830573862722, 500, 1500, "Playlist"], "isController": true}, {"data": [0.3620173364854216, 500, 1500, "Read By Id Genre"], "isController": false}, {"data": [0.37217282795195755, 500, 1500, "Update Artist"], "isController": false}, {"data": [0.3730811837316031, 500, 1500, "Update Genre"], "isController": false}, {"data": [0.4007042798545004, 500, 1500, "Create Artist"], "isController": false}, {"data": [0.3815626930203829, 500, 1500, "Delete Album"], "isController": false}, {"data": [0.36200112422709385, 500, 1500, "Update Playlist"], "isController": false}, {"data": [0.3437882372134758, 500, 1500, "Read By Id Track"], "isController": false}, {"data": [0.3343034536282499, 500, 1500, "Read Artists"], "isController": false}, {"data": [0.3579353990301636, 500, 1500, "Delete Track"], "isController": false}, {"data": [0.37817916734164914, 500, 1500, "Create Track"], "isController": false}, {"data": [0.3560382622558788, 500, 1500, "Read By Id Playlist"], "isController": false}, {"data": [0.3604698423020647, 500, 1500, "Read Track"], "isController": false}, {"data": [0.4029884882213921, 500, 1500, "Create Album"], "isController": false}, {"data": [0.3743453420092049, 500, 1500, "Delete Genre"], "isController": false}, {"data": [0.1746415273706726, 500, 1500, "Genre"], "isController": true}, {"data": [0.37519543464665417, 500, 1500, "Delete Artist"], "isController": false}, {"data": [0.35989095377054214, 500, 1500, "Read By Id Album"], "isController": false}, {"data": [0.1588244938109681, 500, 1500, "Track"], "isController": true}, {"data": [0.37577017868145407, 500, 1500, "Update Album"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 316592, 0, 0.0, 1551.49103893968, 2, 14305, 181.0, 1129.9000000000015, 1740.9500000000007, 3343.780000000035, 310.84265426668344, 869.3255445713581, 83.45710684400086], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Artist", 12805, 0, 0.0, 7772.050449043368, 23, 25076, 9031.0, 13115.4, 14540.099999999997, 17448.280000000006, 12.715811299544992, 136.43050223854883, 16.852948691316605], "isController": true}, {"data": ["Update Track", 12215, 0, 0.0, 1604.5598853868132, 3, 10081, 1529.0, 3112.0, 3809.0, 5597.560000000001, 12.299596625590812, 4.481382519280109, 3.9168502522852178], "isController": false}, {"data": ["Read Genre", 12726, 0, 0.0, 1473.1188904604778, 9, 12942, 1510.0, 2734.0, 3531.0, 5310.409999999993, 12.649835341409375, 147.59313761684646, 2.8536249647124663], "isController": false}, {"data": ["Delete Playlist", 12394, 0, 0.0, 1565.4298047442396, 4, 10985, 1499.0, 3069.0, 3779.5, 5637.0, 12.407983797740053, 2.3143797903987795, 3.188142841670062], "isController": false}, {"data": ["Album", 12974, 0, 0.0, 7691.132187451823, 25, 27440, 8941.5, 13022.0, 14368.25, 17314.75, 12.812080921316403, 174.545114468349, 16.77856094521191], "isController": true}, {"data": ["Read Playlist", 12501, 0, 0.0, 1507.1532677385849, 5, 13868, 1530.0, 2788.0, 3576.7999999999993, 5467.279999999984, 12.502325246476126, 169.9549478416587, 2.856976667651771], "isController": false}, {"data": ["Read Album", 13071, 0, 0.0, 1504.0912707520517, 6, 12378, 1558.0, 2835.800000000001, 3589.199999999999, 5347.240000000005, 12.862714071047527, 159.82987675877024, 2.901647412511698], "isController": false}, {"data": ["Read By Id Artists", 12850, 0, 0.0, 1660.5804669260724, 2, 13453, 1562.5, 3249.0, 4086.0, 5983.49, 12.760738633260344, 4.101702148161113, 3.3041559835823415], "isController": false}, {"data": ["Create Playlist", 12579, 0, 0.0, 1487.5916209555598, 2, 13249, 1461.0, 2864.0, 3625.0, 5639.800000000007, 12.54617919522529, 4.461276566128009, 4.300496970238356], "isController": false}, {"data": ["Create Genre", 12759, 0, 0.0, 1453.493063719727, 2, 11079, 1454.0, 2842.0, 3553.0, 5266.799999999999, 12.683179950257362, 4.212941985933092, 4.000651488215945], "isController": false}, {"data": ["Playlist", 12442, 0, 0.0, 7830.415367304275, 20, 26472, 8984.5, 13076.800000000003, 14480.849999999999, 17199.28, 12.45577608835372, 184.60783446804666, 17.361701064815186], "isController": true}, {"data": ["Read By Id Genre", 12690, 0, 0.0, 1631.7574468085181, 2, 12057, 1533.0, 3212.0, 4003.449999999999, 5836.720000000001, 12.625194874708123, 4.168964960669384, 2.9113771899465046], "isController": false}, {"data": ["Update Artist", 12822, 0, 0.0, 1568.041101232254, 3, 11488, 1505.0, 3081.7000000000007, 3838.0, 5805.77, 12.732642316224734, 4.055345251090348, 3.545542189600881], "isController": false}, {"data": ["Update Genre", 12638, 0, 0.0, 1565.251147333436, 3, 14305, 1499.0, 3053.0, 3814.2499999999964, 5689.440000000002, 12.605000109712773, 3.9899186507847455, 3.8545133761686903], "isController": false}, {"data": ["Create Artist", 12921, 0, 0.0, 1444.6323813946287, 2, 12075, 1450.0, 2763.0, 3497.8999999999996, 5487.120000000003, 12.83217567994343, 4.1748487671463135, 3.5839865668592004], "isController": false}, {"data": ["Delete Album", 12952, 0, 0.0, 1537.08037368746, 4, 11487, 1485.0, 3024.7000000000007, 3769.3499999999985, 5677.759999999995, 12.79250307170202, 2.3861016471631697, 3.250001442946839], "isController": false}, {"data": ["Update Playlist", 12453, 0, 0.0, 1605.6083674616496, 3, 13253, 1521.0, 3136.0, 3928.2999999999993, 5860.8399999999965, 12.467200140960554, 4.2261094370863415, 4.153059436260401], "isController": false}, {"data": ["Read By Id Track", 12259, 0, 0.0, 1672.1583326535606, 2, 12783, 1564.0, 3237.0, 3984.0, 5811.199999999997, 12.322163409637803, 4.5979440612580085, 2.8410731063682437], "isController": false}, {"data": ["Read Artists", 12885, 0, 0.0, 1546.2833527357436, 5, 11515, 1588.0, 2857.0, 3646.0999999999967, 5624.819999999992, 12.795533628866961, 122.65979532825236, 3.248865960454502], "isController": false}, {"data": ["Delete Track", 12167, 0, 0.0, 1591.1460507931267, 4, 13857, 1515.0, 3035.0000000000036, 3819.2000000000007, 5707.399999999994, 12.250955800098073, 2.2850903884948552, 3.1117053763189135], "isController": false}, {"data": ["Create Track", 12346, 0, 0.0, 1500.1423132998593, 2, 8836, 1477.0, 2861.300000000001, 3569.6499999999996, 5369.650000000003, 12.396615380598986, 4.65001432881706, 4.007109073220961], "isController": false}, {"data": ["Read By Id Playlist", 12545, 0, 0.0, 1651.803268234359, 2, 11802, 1548.0, 3249.0, 4059.699999999999, 5959.699999999995, 12.52437235872518, 4.429031556900784, 2.9246391739679747], "isController": false}, {"data": ["Read Track", 12302, 0, 0.0, 1497.501463176717, 3, 9686, 1516.0, 2742.4000000000015, 3523.7999999999884, 5403.849999999997, 12.365322818151755, 205.36932031275913, 2.7894429404229055], "isController": false}, {"data": ["Create Album", 13117, 0, 0.0, 1442.8014789967194, 2, 11183, 1455.0, 2819.0, 3523.0999999999985, 5376.119999999995, 12.878793829332663, 4.4417142178884355, 3.8485458122810496], "isController": false}, {"data": ["Delete Genre", 12602, 0, 0.0, 1539.447071893346, 4, 11201, 1496.0, 3017.0, 3767.8499999999985, 5443.969999999999, 12.569031430557647, 2.344418948473155, 3.1929383991415516], "isController": false}, {"data": ["Genre", 12623, 0, 0.0, 7669.392854313554, 21, 24419, 8854.0, 12877.2, 14155.199999999997, 16686.56, 12.58970024285764, 161.34132251444927, 16.757050647911274], "isController": true}, {"data": ["Delete Artist", 12792, 0, 0.0, 1548.0171200750412, 4, 11229, 1497.0, 3025.0, 3739.3499999999985, 5768.769999999997, 12.715958567764767, 2.371824303167061, 3.2428412080135787], "isController": false}, {"data": ["Read By Id Album", 13022, 0, 0.0, 1634.2727691598873, 2, 13044, 1549.5, 3244.5000000000036, 4021.0, 5675.77, 12.861615652809466, 4.385472339612532, 2.9661729560505496], "isController": false}, {"data": ["Track", 12199, 0, 0.0, 7876.146159521251, 17, 26964, 9024.0, 12928.0, 14273.0, 17087.0, 12.283188977250072, 219.63673097441114, 16.596712854278223], "isController": true}, {"data": ["Update Album", 12984, 0, 0.0, 1565.3767714109679, 3, 12550, 1506.0, 3088.0, 3846.25, 5644.899999999998, 12.824121675076816, 4.347609871173579, 3.859191174564208], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 316592, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
