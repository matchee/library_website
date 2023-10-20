/**
 * Created by wangshuo on 2018/2/2.
 */

var header_menu = document.getElementById("header_menu");
var menu_divs = header_menu.getElementsByTagName("div");
var header = document.getElementById("header");
var banner_img = document.getElementById("banner_img");
var color_arr = ["#5d0b11","#dae4e5","#54514b","#733f3b","#afa7a5","#291521"];
//右侧菜单效果
for (var i = 0; i < menu_divs.length; i++) {
    var menu_div = menu_divs[i];
    menu_div.index = i;
    menu_div.onmouseover = function () {
        for (var j = 0; j < menu_divs.length; j++) {
            menu_divs[j].className = "";
        }
        this.className = "menu-active";
        banner_img.style.background = "url('images/banner" + this.index + ".png') no-repeat center center";
        header.style.backgroundColor = color_arr[this.index];
    };
}



//搜索框聚焦 下拉显示，点击空白消失

var text_input = document.getElementById("text_input");
var search_drop = document.getElementById("search_drop");

text_input.addEventListener("focus", function () {
    search_drop.style.display = "block";
});

//点击空白 下拉消失
document.onclick = function (event) {
    var event = event || window.event;
    //event.target.id 火狐谷歌支持  event.srcElement.id ie支持  如果支持event.target的话，就用前者  否则后者
    var targetId = event.target ? event.target.id : event.srcElement.id;
    //当点击页面的时候  只要没有点到下拉菜单和输入框 就相当于点击空白处  那么这时候下拉消失
    if (targetId != "search_drop" && targetId != "text_input")
    {
        search_drop.style.display = "none";
    }
};


//向下拉菜单中动态添加数据
var num = 0;//列表序号
var result_arr = ["欢乐喜剧人第三季 郭德纲","恋爱大师 靳东携手江疏影","谈判官 杨幂携新作归来","我是歌手第五季强势归来","三分钟 陈可辛催泪三分钟","寻秦记 小鲜肉版寻秦记"];
for (var key in result_arr) {
    var that  = key;//这里注意  在这里保存一下key的值  因为下边就把key变成0+key了
    var xuhao = parseInt(key) + 1;//注意  这里的key是string
    //console.log(xuhao);
    num = key < 10 ? key = "0" + xuhao : xuhao;
    var data_in = "<span>" + num + "</span><p>" + result_arr[that] + "</p>";
    var div = document.createElement("div");
    div.innerHTML = data_in;//注意data_in不是节点  不能用appendchild直接添加
    search_drop.appendChild(div);
}

//下拉菜单的前三个序号 是橘红色
var spans = search_drop.getElementsByTagName("span");
for (var i = 0; i < 3; i++) {
    spans[i].className = "hot-word";
}
//鼠标划过下拉菜单时 背景颜色变化
var divs = search_drop.getElementsByTagName("div");
for (var i = 0; i < divs.length; i++) {
    var div = divs[i];
    div.addEventListener("mouseover", function () {
        for (var j = 0; j < divs.length; j++) {
            divs[j].className = "";
        }
        this.className = "div-hover";
    })

    div.addEventListener("mouseout", function () {
        for (var j = 0; j < divs.length; j++) {
            divs[j].className = "";
        }
    })
}



