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

    var data = {"OkPercent": 99.99979459165702, "KoPercent": 2.0540834298263478E-4};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.023201018014582967, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.01641901441973211, 500, 1500, "Artist"], "isController": true}, {"data": [0.02204921482203371, 500, 1500, "Update Track"], "isController": false}, {"data": [0.025435348462074275, 500, 1500, "Read Genre"], "isController": false}, {"data": [0.02321402268375313, 500, 1500, "Delete Playlist"], "isController": false}, {"data": [0.017366637947399934, 500, 1500, "Album"], "isController": true}, {"data": [0.023757328762588553, 500, 1500, "Read Playlist"], "isController": false}, {"data": [0.026911190156860457, 500, 1500, "Read Album"], "isController": false}, {"data": [0.025038774435210114, 500, 1500, "Read By Id Artists"], "isController": false}, {"data": [0.0243803869412828, 500, 1500, "Create Playlist"], "isController": false}, {"data": [0.02565915786079641, 500, 1500, "Create Genre"], "isController": false}, {"data": [0.014819478501631064, 500, 1500, "Playlist"], "isController": true}, {"data": [0.024440613307901107, 500, 1500, "Read By Id Genre"], "isController": false}, {"data": [0.025241396475867674, 500, 1500, "Update Artist"], "isController": false}, {"data": [0.024392034235464242, 500, 1500, "Update Genre"], "isController": false}, {"data": [0.02668883124474809, 500, 1500, "Create Artist"], "isController": false}, {"data": [0.02635941402427447, 500, 1500, "Delete Album"], "isController": false}, {"data": [0.02340276755483586, 500, 1500, "Update Playlist"], "isController": false}, {"data": [0.022206726583137795, 500, 1500, "Read By Id Track"], "isController": false}, {"data": [0.02557175445266575, 500, 1500, "Read Artists"], "isController": false}, {"data": [0.022083591880152468, 500, 1500, "Delete Track"], "isController": false}, {"data": [0.023269782078398714, 500, 1500, "Create Track"], "isController": false}, {"data": [0.02323239006888845, 500, 1500, "Read By Id Playlist"], "isController": false}, {"data": [0.023212600283152432, 500, 1500, "Read Track"], "isController": false}, {"data": [0.028144236022582408, 500, 1500, "Create Album"], "isController": false}, {"data": [0.02441433502239847, 500, 1500, "Delete Genre"], "isController": false}, {"data": [0.015680299581467067, 500, 1500, "Genre"], "isController": true}, {"data": [0.025338382210763778, 500, 1500, "Delete Artist"], "isController": false}, {"data": [0.026361474608662668, 500, 1500, "Read By Id Album"], "isController": false}, {"data": [0.014202468260474598, 500, 1500, "Track"], "isController": true}, {"data": [0.026516699754758847, 500, 1500, "Update Album"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3407846, 7, 2.0540834298263478E-4, 9791.65583920184, 1, 40457, 2206.0, 5491.9000000000015, 6937.950000000001, 10208.960000000006, 181.2825790101673, 1058.3106170423466, 48.81553155353105], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["Artist", 136549, 2, 0.001464675684186629, 49127.41172765808, 22, 89426, 50102.5, 57672.9, 60353.700000000004, 66584.95000000001, 7.268026114201088, 163.40873619261197, 9.66093262179076], "isController": true}, {"data": ["Update Track", 135447, 0, 0.0, 9867.104402460121, 3, 35587, 9414.0, 12732.900000000001, 14426.0, 18513.93000000001, 7.216794484104985, 2.6384119667605836, 2.3071723761815464], "isController": false}, {"data": ["Read Genre", 136385, 0, 0.0, 9630.23616233461, 4, 34706, 9404.0, 12212.0, 13834.900000000001, 17789.960000000006, 7.260104992451375, 168.53576583514416, 1.6377775910705739], "isController": false}, {"data": ["Delete Playlist", 135780, 1, 7.364854912358227E-4, 9749.889365149542, 4, 40097, 9380.5, 12574.900000000001, 14256.0, 18218.860000000022, 7.2304866456191945, 1.349432934836717, 1.8667476589582126], "isController": false}, {"data": ["Album", 136958, 1, 7.301508491654376E-4, 48978.497919070076, 23, 86962, 50085.0, 57737.40000000001, 60519.9, 66786.97, 7.2876018289082785, 209.78710923282821, 9.574127993279436], "isController": true}, {"data": ["Read Playlist", 135937, 0, 0.0, 9692.49962850453, 4, 37625, 9439.0, 12229.900000000001, 13911.600000000006, 17880.970000000005, 7.237875080378978, 221.468301097017, 1.6539675476647275], "isController": false}, {"data": ["Read Album", 137192, 1, 7.289054755379322E-4, 9715.310418975043, 5, 38861, 9510.5, 12269.900000000001, 13804.850000000002, 17977.980000000003, 7.299959858521508, 201.29494995938563, 1.646768288396942], "isController": false}, {"data": ["Read By Id Artists", 136688, 1, 7.315931171719537E-4, 10156.146377151032, 2, 38943, 9740.0, 13462.900000000001, 15209.750000000004, 19459.900000000016, 7.274133364185037, 2.347751112110143, 1.8923491437401811], "isController": false}, {"data": ["Create Playlist", 136093, 0, 0.0, 9579.861756299037, 2, 35466, 9291.0, 12164.0, 13869.900000000001, 18079.74000000004, 7.245666551755807, 2.5853374311366726, 2.4836220309241095], "isController": false}, {"data": ["Create Genre", 136462, 0, 0.0, 9554.711853849436, 2, 34705, 9271.0, 12209.900000000001, 13835.850000000002, 17974.860000000022, 7.263364479932416, 2.421467446797806, 2.291080788103682], "isController": false}, {"data": ["Playlist", 135801, 2, 0.0014727432051310373, 48956.7166883896, 18, 89749, 49880.0, 57263.30000000001, 60109.8, 66061.94, 7.231606467181236, 230.23026748950548, 10.113514894060808], "isController": true}, {"data": ["Read By Id Genre", 136310, 0, 0.0, 10067.267507886347, 1, 37750, 9643.0, 13243.0, 14838.800000000003, 19073.610000000062, 7.25627749717143, 2.4049274821575475, 1.682134215837737], "isController": false}, {"data": ["Update Artist", 136601, 0, 0.0, 9830.36401636888, 3, 35148, 9427.0, 12872.0, 14563.0, 18580.830000000027, 7.270791957498433, 2.3246004806245724, 2.0334847870137955], "isController": false}, {"data": ["Update Genre", 136233, 0, 0.0, 9798.908634471758, 3, 38917, 9398.0, 12749.900000000001, 14417.900000000001, 18579.900000000016, 7.2525271356885845, 2.304526389792869, 2.2266183834524647], "isController": false}, {"data": ["Create Artist", 136855, 0, 0.0, 9558.003668115985, 2, 35495, 9297.0, 12219.0, 13882.900000000001, 17860.57000000007, 7.282192446761323, 2.378034070452784, 2.0338935935290414], "isController": false}, {"data": ["Delete Album", 136934, 0, 0.0, 9743.844070866216, 4, 36320, 9403.0, 12647.0, 14342.0, 18396.970000000005, 7.286394565497096, 1.3590833613378372, 1.8603346357910837], "isController": false}, {"data": ["Update Playlist", 135860, 1, 7.360518180479906E-4, 9814.383409391783, 3, 39578, 9398.0, 12674.900000000001, 14366.900000000001, 18704.920000000013, 7.2347502317772, 2.462095969972272, 2.418935516614205], "isController": false}, {"data": ["Read By Id Track", 135522, 2, 0.0014757751508980093, 10131.67860568761, 2, 40457, 9672.0, 13290.0, 15001.450000000008, 19311.0, 7.219069312057138, 2.704228813107329, 1.6734135807269888], "isController": false}, {"data": ["Read Artists", 136772, 1, 7.31143801362852E-4, 9797.44245898261, 5, 39382, 9566.0, 12380.900000000001, 14046.700000000004, 17908.860000000022, 7.278602822451573, 155.2463852033788, 1.8480827478880943], "isController": false}, {"data": ["Delete Track", 135372, 0, 0.0, 9793.61962591999, 4, 37120, 9379.0, 12579.800000000003, 14314.95, 18505.830000000027, 7.2127953109051415, 1.3453553753739083, 1.8410041951426324], "isController": false}, {"data": ["Create Track", 135691, 0, 0.0, 9599.176533447177, 2, 37227, 9304.0, 12208.900000000001, 13700.850000000002, 18046.860000000022, 7.226499205272019, 2.7195963831355763, 2.3359094110791387], "isController": false}, {"data": ["Read By Id Playlist", 136017, 0, 0.0, 10098.848599807352, 2, 40085, 9663.0, 13285.800000000003, 14947.900000000001, 19000.94000000001, 7.2421735732039, 2.5699437560432643, 1.7000342350431865], "isController": false}, {"data": ["Read Track", 135616, 0, 0.0, 9647.827498230221, 2, 36033, 9387.0, 12097.800000000003, 13684.850000000002, 18021.460000000086, 7.222471848597784, 268.41421321794337, 1.629288083033289], "isController": false}, {"data": ["Create Album", 137275, 0, 0.0, 9558.751411400428, 2, 36207, 9307.5, 12292.0, 13990.0, 17923.980000000003, 7.304367333241068, 2.5283181540239523, 2.1827503945036786], "isController": false}, {"data": ["Delete Genre", 136170, 0, 0.0, 9701.06652713532, 4, 38338, 9356.0, 12557.900000000001, 14216.95, 18360.99, 7.2491713255426795, 1.352140354666652, 1.8503848294926175], "isController": false}, {"data": ["Genre", 136190, 0, 0.0, 48769.894294734746, 17, 88472, 49751.0, 57167.9, 59833.95, 65998.82000000004, 7.250231803523243, 176.78256980702147, 9.679513597448674], "isController": true}, {"data": ["Delete Artist", 136532, 0, 0.0, 9765.37989628827, 4, 39230, 9411.0, 12661.600000000006, 14287.0, 18327.99, 7.26711778237619, 1.355487789486184, 1.862131941218698], "isController": false}, {"data": ["Read By Id Album", 137094, 0, 0.0, 10124.874108276013, 2, 38886, 9746.0, 13460.900000000001, 15136.900000000001, 19217.710000000046, 7.294913371107846, 2.49654480600122, 1.691539717197327], "isController": false}, {"data": ["Track", 135399, 2, 0.001477115783720707, 49059.84914216557, 17, 86539, 49765.5, 57313.9, 60101.95, 66315.92000000001, 7.214243131060056, 277.5032608693046, 9.77903202677399], "isController": true}, {"data": ["Update Album", 137008, 0, 0.0, 9815.465695433764, 3, 35515, 9439.0, 12761.700000000004, 14653.95, 18851.76000000004, 7.290332955074057, 2.4807358318158, 2.2030766665346593], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["500", 7, 100.0, 2.0540834298263478E-4], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 3407846, 7, "500", 7, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Delete Playlist", 135780, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Read Album", 137192, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Read By Id Artists", 136688, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Update Playlist", 135860, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Read By Id Track", 135522, 2, "500", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Read Artists", 136772, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
