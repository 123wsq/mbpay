<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
    <table>
        <tr>
        	<c:if test="${FILETYPE == 'ISNOSHOW'}">
        		<td width="400px">
	                <c:if test="${!empty FJSHOW}">
	                    <img width="${WIDTH}" height="${HEIGHT}" src="${FJSHOW}"/>
	                </c:if>
	            </td>
        	</c:if>
        	<c:if test="${FILETYPE == 'ISIMAGE'}">
        		<td width="400px">
	                <c:if test="${!empty FJSHOW}">
	                    <img width="${WIDTH}" height="${HEIGHT}" src="${FJSHOW}"/>
	                </c:if>
	            </td>
        	</c:if>
            <c:if test="${FILETYPE == 'ISSWF'}">
        		<td width="400px">
	                <c:if test="${!empty FJSHOW}">
	                    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="680" height="550" id="KeyBoardDemo">
					        <param name="movie" value="${FJSHOW}" />
					        <param name="quality" value="high" />
					        <param name="bgcolor" value="#f2f2f2" />
					        <param name="allowScriptAccess" value="sameDomain" />
					        <param name="allowFullScreen" value="true" />
					        <!--[if !IE]>-->
					        <object type="application/x-shockwave-flash" data="${FJSHOW}" width="100%" height="100%">
					            <param name="quality" value="high" />
					            <param name="bgcolor" value="#f2f2f2" />
					            <param name="allowScriptAccess" value="sameDomain" />
					            <param name="allowFullScreen" value="true" />
					        <!--<![endif]-->
					        <!--[if gte IE 6]>-->
					            <p> 
					                Either scripts and active content are not permitted to run or Adobe Flash Player version
					                10.2.0 or greater is not installed.
					            </p>
					        <!--<![endif]-->
					            <a href="http://www.adobe.com/go/getflashplayer">
					                <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
					            </a>
					        <!--[if !IE]>-->
					        </object>
					        <!--<![endif]-->
					    </object>
	                </c:if>
	            </td>
        	</c:if>
        	<c:if test="${FILETYPE == 'ISMEDIA'}">
        		<td width="400px">
	                <c:if test="${!empty FJSHOW}">
	                    <OBJECT ID=video1 CLASSID="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA"  HEIGHT=400 WIDTH=600> 
					        <param name="_ExtentX" value="9313"> 
					        <param name="_ExtentY" value="7620"> 
					        <param name="AUTOSTART" value="0"> 
					        <param name="SHUFFLE" value="0"> 
					        <param name="PREFETCH" value="0"> 
					        <param name="NOLABELS" value="0"> 
					        <param name="SRC" value="${FJSHOW}"> 
					        <param name="CONTROLS" value="ImageWindow"> 
					        <param name="CONSOLE" value="Clip1"> 
					        <param name="LOOP" value="0"> 
					        <param name="NUMLOOP" value="0"> 
					        <param name="CENTER" value="0">
					        <param name="scale" value="tofit" />
					        <param name="controller" value="true" /> 
					        <param name="autoplay" value="false" />
					        <param name="ShowAudioControls" value="-1">
					        <param name="MAINTAINASPECT" value="0"> 
					        <param name="BACKGROUNDCOLOR" value="#000000">
					        <EMBED SRC="${FJSHOW}" type="video/quicktime"
					            PLUGINSPAGE="http://www.apple.com/quicktime/download" WIDTH="1000"
					            HEIGHT="600" scale="tofit" controller="true" autoplay="true"></EMBED> 
					    </OBJECT>
	                </c:if>
	            </td>
        	</c:if>
        </tr>
    </table>
</div>