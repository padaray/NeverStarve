<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>NeverStarve</title>
 <style>
        *{
            padding: 0%;
            margin: 0;
        }
        body {
            /* background-image: url("images/indexbackground.jpg"); */
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: center;
            background-size: cover
        }
        #headbar{
            display: flex;
            align-items: center;
            position: relative;
            justify-content: space-between;
        }
        

        .logodiv{
            width: 379px;
            float: left;
        }
        
        .headform{
            position: absolute;
            
            right:100px;
        }

        .form-group{
            width: 200px;
            margin: 0;
            float: left ;
        }
        .longin{
            border-radius: 20px;
            position: absolute;
            right: 50px;
            background-color: seagreen;
            padding: 5px;
            font-weight: 600;
            
        }
        a{
            text-decoration:none;
        }
        a:link {
	    color: yellow; /*未訪問連接颜色*/
        }
 
        a:visited {
	    color: rgb(255, 255, 255); /*已訪問的連接颜色*/
        }
 
        a:hover {
	    /* color: blue; 移動到連接的颜色 */
	    text-decoration: underline;
        }
 
        a:active {
	    color: orange; /*點擊的颜色*/
        }


    </style>


</head>
<body>
      <div id="headbar" style="background-color: #C4E1E1; ">
            <div class="logodiv">
                <img src='<c:url value="/images/NeverStarvelogo3.png"/>' height="125px">
                
            </div>

            <div class="headform">
                <form role="search" >
                    <div class="form-group">
                        <input type="text"  />    
                        <button type="submit"><i class="fa fa-search"></i></button>
                    </div>                                                   
                </form>
            </div>
            <div class="longin"><a href="https://tw.yahoo.com/"><p>登入</p></a></div>
        </div>


</body>
</html>