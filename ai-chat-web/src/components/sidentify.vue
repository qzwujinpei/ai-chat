<template>
  <div class="s-canvas">
    <canvas id="s-canvas" :height="props.contentHeight" :width="props.contentWidth"></canvas>
  </div>
</template>

<script setup>
import { onMounted, watch } from 'vue'

// eslint-disable-next-line no-undef
const props = defineProps({
  identifyCode: {
    type: String,
    default: '1234',
  },
  fontSizeMin: {
    type: Number,
    default: 25,
  },
  fontSizeMax: {
    type: Number,
    default: 35,
  },
  backgroundColorMin: {
    type: Number,
    default: 255,
  },
  backgroundColorMax: {
    type: Number,
    default: 255,
  },
  colorMin: {
    type: Number,
    default: 0,
  },
  colorMax: {
    type: Number,
    default: 160,
  },
  lineColorMin: {
    type: Number,
    default: 40,
  },
  lineColorMax: {
    type: Number,
    default: 180,
  },
  dotColorMin: {
    type: Number,
    default: 0,
  },
  dotColorMax: {
    type: Number,
    default: 255,
  },
  contentWidth: {
    type: Number,
    default: 112,
  },
  contentHeight: {
    type: Number,
    default: 40,
  },
})
// 生成一个随机数
const randomNum = (min, max) => {
  return Math.floor(Math.random() * (max - min) + min)
}

// 生成一个随机的颜色
const randomColor = (min, max) => {
  let r = randomNum(min, max)
  let g = randomNum(min, max)
  let b = randomNum(min, max)
  return 'rgb(' + r + ',' + g + ',' + b + ')'
}

// 绘制干扰线
const drawLine = (ctx) => {
  for (let i = 0; i < 5; i++) {
    ctx.strokeStyle = randomColor(props.lineColorMin, props.lineColorMax)
    ctx.beginPath()
    ctx.moveTo(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight))
    ctx.lineTo(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight))
    ctx.stroke()
  }
}
//在画布上显示数据
const drawText = (ctx, txt, i) => {
  ctx.fillStyle = randomColor(props.colorMin, props.colorMax)
  ctx.font = randomNum(props.fontSizeMin, props.fontSizeMax) + 'px SimHei'
  let x = (i + 1) * (props.contentWidth / (props.identifyCode.length + 1))
  let y = randomNum(props.fontSizeMax, props.contentHeight - 5)
  var deg = randomNum(-45, 45)
  // 修改坐标原点和旋转角度
  ctx.translate(x, y)
  ctx.rotate((deg * Math.PI) / 180)
  ctx.fillText(txt, 0, 0)
  // 恢复坐标原点和旋转角度
  ctx.rotate((-deg * Math.PI) / 180)
  ctx.translate(-x, -y)
}
// 绘制干扰点
const drawDot = (ctx) => {
  for (let i = 0; i < 30; i++) {
    ctx.fillStyle = randomColor(0, 255)
    ctx.beginPath()
    ctx.arc(randomNum(0, props.contentWidth), randomNum(0, props.contentHeight), 1, 0, 2 * Math.PI)
    ctx.fill()
  }
}
//画图
const drawPic = () => {
  let canvas = document.getElementById('s-canvas')
  let ctx = canvas.getContext('2d')
  ctx.textBaseline = 'bottom'
  // 绘制背景
  ctx.fillStyle = randomColor(props.backgroundColorMin, props.backgroundColorMax)
  ctx.fillRect(0, 0, props.contentWidth, props.contentHeight)
  // 绘制文字
  for (let i = 0; i < 4; i++) {
    drawText(ctx, props.identifyCode[i], i)
  }
  // drawLine(ctx)
  drawDot(ctx)
}
//组件挂载
onMounted(() => {
  drawPic()
})
// 监听
watch(
  () => props.identifyCode,
  () => {
    drawPic()
  },
)
</script>
<style lang="scss" scoped>
.s-canvas {
  cursor: pointer;
}
</style>
