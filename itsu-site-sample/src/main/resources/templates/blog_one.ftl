<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
  <title></title>
</head>
<style type="text/css">
  #page {
    width: 100%;
    margin: auto;
  }

  img {
    vertical-align: middle;
    object-fit: cover;
	max-width:100%;
  }

  .medai-box img {
    width: 100%;
    border-radius: 10px;
  }

  #time-box {
    color: #3c3c3c;
  }

  #title-box {
    color: #3c3c3c;
  }

  a {
    color: #0000ff;
  }

</style>

<body>
  <div id="page">
    <div class="medai-box">
      <#if media.mediaType=='photo'>
        <img src="${media.mediaUrl}"/>
      <#elseif media.mediaType=='video'>
        <video src="${media.mediaUrl}" controls="controls"></video>
      <#else>
        <p>媒体资源无法识别</p>
      </#if>
    </div>
    <div id="time-box">
      <p>${time!}</p>
    </div>
    <div id="title-box">
      <h1>${title!}</h1>
    </div>
    <div id="text-box">
      <p>${text.text}</p>
    </div>
  </div>

</body>
<script>
</script>

</html>