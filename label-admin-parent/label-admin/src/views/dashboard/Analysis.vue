<template>
  <div class="page-header-index-wide">
    <div class="table-page-search-wrapper">
      <a-row :gutter="80">
        <a-col :md="1" :sm="2">
          <a-select placeholder="请选择年份" v-model="year">
            <a-select-option value="2019">2019</a-select-option>
            <a-select-option value="2020">2020</a-select-option>
          </a-select>
        </a-col>
        <a-col :md="1" :sm="2">
          <a-select placeholder="请选择月份" v-model="month">
            <a-select-option v-for="index of 12" :key="index" :value="index">{{index}}</a-select-option>
          </a-select>
        </a-col>
        <a-col :md="1" :sm="2">
          <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
            <a-button type="primary" @click="getIndex(year,month)" icon="search">查询</a-button>
          </span>
        </a-col>
      </a-row>
    </div>
    <a-row :gutter="24" v-if="dataSource.saleResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总销售额" :total="'￥'+dataSource.saleResponse.totalSale">
          <a-tooltip title="总销售额" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            今日销售额
            <span>￥ {{dataSource.saleResponse.todaySale}}</span>
          </template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="总销售数"
          :total="dataSource.numberSaleResponse.totalNumberSale+'份'"
        >
          <a-tooltip title="总销售数" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            今日销售数
            <span>{{dataSource.numberSaleResponse.todayNumberSale}}份</span>
          </template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="总人数"
          :total="dataSource.userResponse.totalNumbers+'人'"
        >
          <a-tooltip title="总人数" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            今日增长
            <span>{{dataSource.userResponse.todayNumbers}}人</span>
          </template>
        </chart-card>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.saleResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="销售额柱状图（年）（单位：元）" :dataSource="sale" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="销售数柱状图（年）（单位：份）" :dataSource="numberSale" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="新增人数柱状图（年）（单位：人）" :dataSource="userNumbers" />
        </template>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.saleResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="销售额柱状图（月）（单位：元）" :dataSource="daySale" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="销售数柱状图（月）（单位：份）" :dataSource="dayNumberSale" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="新增人数柱状图（月）（单位：人）" :dataSource="userDayNumbers" />
        </template>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.memberIncomeResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="用户总收入"
          :total="'￥'+dataSource.memberIncomeResponse.totalIncome"
        >
          <a-tooltip title="用户总收入" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            今日用户收入
            <span>￥ {{dataSource.memberIncomeResponse.todayIncome}}</span>
          </template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="平台收入"
          :total="'￥'+dataSource.platformIncomeResponse.totalIncome"
        >
          <a-tooltip title="平台收入" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            平台今日余额
            <span>￥ {{dataSource.platformIncomeResponse.todayIncome}}</span>
          </template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="总余额"
          :total="'￥'+dataSource.platformBalanceResponse.totalBalance"
        >
          <a-tooltip title="总余额" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            今日余额
            <span>￥ {{dataSource.platformBalanceResponse.todayBalance}}</span>
          </template>
        </chart-card>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.memberIncomeResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="用户收入（年）（单位：元）" :dataSource="userIncome" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="平台收入（年）（单位：元）" :dataSource="platformIncome" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="平台余额（年）（单位：元）" :dataSource="platformBalance" />
        </template>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.memberIncomeResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="用户收入（月）（单位：元）" :dataSource="userDayIncome" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="平台收入（月）（单位：元）" :dataSource="platformDayIncome" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="平台余额（月）（单位：元）" :dataSource="platformDayBalance" />
        </template>
      </a-col>
    </a-row>

    <a-row :gutter="24" v-if="dataSource.saleGiftResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="玫瑰总数"
          :total="dataSource.saleGiftResponse.totalSale+'支'"
        >
          <a-tooltip title="玫瑰总数" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            今日玫瑰新增
            <span>{{dataSource.saleGiftResponse.todaySale}}支</span>
          </template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="使用玫瑰数量"
          :total="dataSource.useGiftResponse.totalUse+'支'"
        >
          <a-tooltip title="使用玫瑰数量" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            平台今日使用玫瑰数量
            <span>{{dataSource.useGiftResponse.todayUse}}支</span>
          </template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <chart-card
          :loading="loading"
          title="玫瑰剩余总数"
          :total="dataSource.unusedGiftResponse.totalUnused+'支'"
        >
          <a-tooltip title="玫瑰剩余总数" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <template slot="footer">
            今日剩余玫瑰
            <span>{{dataSource.unusedGiftResponse.todayUnused}}支</span>
          </template>
        </chart-card>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.saleGiftResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="玫瑰数量（年）（单位：支）" :dataSource="saleGift" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="玫瑰使用数量（年）（单位：支）" :dataSource="useGift" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="玫瑰剩余数量（年）（单位：支）" :dataSource="unusedGift" />
        </template>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.saleGiftResponse">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="玫瑰数量（月）（单位：支）" :dataSource="daySaleGift" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="玫瑰使用数量（月）（单位：支）" :dataSource="dayUseGift" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="玫瑰剩余数量（月）（单位：支）" :dataSource="dayUnusedGift" />
        </template>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.agentDetailsVo">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="机构月收入（月）（单位：元）" :dataSource="monthOwnerIncome" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="16" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="机构日收入（日）（单位：元）" :dataSource="dayOwnerIncome" />
        </template>
      </a-col>
    </a-row>
    <a-row :gutter="24" v-if="dataSource.agentDetailsVo">
      <a-col :sm="24" :md="12" :xl="8" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="机构旗下红娘月收入（月）（单位：元）" :dataSource="monthOtherIncome" />
        </template>
      </a-col>
      <a-col :sm="24" :md="12" :xl="16" :style="{ marginBottom: '24px' }">
        <template>
          <bar title="机构旗下红娘日收入（日）（单位：元）" :dataSource="dayOtherIncome" />
        </template>
      </a-col>
    </a-row>
  </div>
</template>

    </a-row>
  </div>
</template>

<script>
import ChartCard from '@/components/ChartCard'
import ACol from 'ant-design-vue/es/grid/Col'
import ATooltip from 'ant-design-vue/es/tooltip/Tooltip'
import Bar from '@/components/chart/Bar'
import MiniArea from '@/components/chart/MiniArea'
import MiniBar from '@/components/chart/MiniBar'
import MiniProgress from '@/components/chart/MiniProgress'
import { getLoginfo } from '@/api/api'
import { getAction } from '@/api/manage'

export default {
  name: 'Analysis',
  components: {
    ATooltip,
    ACol,
    ChartCard,
    Bar,
    MiniArea,
    MiniBar,
    MiniProgress
  },
  data() {
    return {
      loading: true,
      center: null,
      year: 2020,
      month: 1,
      loginfo: {},
      dataSource: {},
      userNumbers: [],
      userDayNumbers: [],
      sale: [],
      daySale: [],
      numberSale: [],
      dayNumberSale: [],
      userIncome: [],
      userDayIncome: [],
      platformIncome: [],
      platformDayIncome: [],
      platformBalance: [],
      platformDayBalance: [],
      saleGift: [],
      daySaleGift: [],
      useGift: [],
      dayUseGift: [],
      unusedGift: [],
      dayUnusedGift: [],
      monthOwnerIncome: [],
      dayOwnerIncome: [],
      monthOtherIncome: [],
      dayOtherIncome: [],
      url: {
        index: '/index/home-page'
      }
    }
  },
  created() {
    setTimeout(() => {
      this.loading = !this.loading
    }, 500)
    var now = new Date()
    this.year = now.getFullYear()
    this.month = now.getMonth() + 1
    this.getIndex(this.year, this.month)
    this.initLogInfo()
  },
  methods: {
    initLogInfo() {
      getLoginfo(null).then(res => {
        if (res.success) {
          this.loginfo = res.result
        }
      })
    },
    getIndex(year, month) {
      year = year || this.year
      month = month || this.month
      console.log(year, month)
      var param = {
        year: year,
        month: month
      }
      this.dataSource = {}
      this.userNumbers = []
      this.userDayNumbers = []
      this.sale = []
      this.daySale = []
      this.numberSale = []
      this.dayNumberSale = []
      this.userIncome = []
      this.userDayIncome = []
      this.platformIncome = []
      this.platformDayIncome = []
      this.platformBalance = []
      this.platformDayBalance = []
      this.saleGift = []
      this.daySaleGift = []
      this.useGift = []
      this.dayUseGift = []
      this.unusedGift = []
      this.dayUnusedGift = []
      this.monthOwnerIncome = []
      this.dayOwnerIncome = []
      this.monthOtherIncome = []
      this.dayOtherIncome = []
      getAction(this.url.index, param).then(res => {
        if (res.success) {
          this.dataSource = res.result
          var tmps = []
          if (res.result.userResponse) {
            tmps = res.result.userResponse.monthNumbers
            //销售额 销售数 用户数
            tmps.forEach(element => {
              this.userNumbers.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.userResponse.dayNumbers
            tmps.forEach(element => {
              this.userDayNumbers.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
          }
          if (res.result.saleResponse) {
            tmps = res.result.saleResponse.monthSale
            tmps.forEach(element => {
              this.sale.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.saleResponse.daySale
            tmps.forEach(element => {
              this.daySale.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
          }
          if (res.result.numberSaleResponse) {
            tmps = res.result.numberSaleResponse.monthNumberSale
            tmps.forEach(element => {
              this.numberSale.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.numberSaleResponse.dayNumberSale
            tmps.forEach(element => {
              this.dayNumberSale.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
          }
          //用户收入 平台收入 平台余额
          if (res.result.memberIncomeResponse) {
            tmps = res.result.memberIncomeResponse.monthIncome
            tmps.forEach(element => {
              this.userIncome.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.memberIncomeResponse.dayIncome
            tmps.forEach(element => {
              this.userDayIncome.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.platformIncomeResponse.monthIncome
            tmps.forEach(element => {
              this.platformIncome.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.platformIncomeResponse.dayIncome
            tmps.forEach(element => {
              this.platformDayIncome.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.platformBalanceResponse.monthBalance
            tmps.forEach(element => {
              this.platformBalance.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.platformBalanceResponse.dayBalance
            tmps.forEach(element => {
              this.platformDayBalance.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
          }
          //礼物销售数 礼物使用数 礼物余额
          if (res.result.saleGiftResponse) {
            tmps = res.result.saleGiftResponse.monthSale
            tmps.forEach(element => {
              this.saleGift.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.saleGiftResponse.daySale
            tmps.forEach(element => {
              this.daySaleGift.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.useGiftResponse.monthUse
            tmps.forEach(element => {
              this.useGift.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.useGiftResponse.dayUse
            tmps.forEach(element => {
              this.dayUseGift.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.unusedGiftResponse.monthUnused
            tmps.forEach(element => {
              this.unusedGift.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.unusedGiftResponse.dayUnused
            tmps.forEach(element => {
              this.dayUnusedGift.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
          }
          //代理收入
          if (res.result.agentDetailsVo) {
            tmps = res.result.agentDetailsVo.monthOwnerIncome
            tmps.forEach(element => {
              this.monthOwnerIncome.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.agentDetailsVo.dayOwnerIncome
            tmps.forEach(element => {
              this.dayOwnerIncome.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.agentDetailsVo.monthOtherIncome
            tmps.forEach(element => {
              this.monthOtherIncome.push({
                x: `${element.month}月`,
                y: parseFloat(element.value)
              })
            })
            tmps = res.result.agentDetailsVo.dayOtherIncome
            tmps.forEach(element => {
              this.dayOtherIncome.push({
                x: `${element.day}日`,
                y: parseFloat(element.value)
              })
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
/* 首页访问量统计 */
</style>