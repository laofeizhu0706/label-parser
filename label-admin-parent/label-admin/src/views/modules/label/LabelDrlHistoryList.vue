<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>

        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <labelDrlHistory-modal ref="modalForm" @ok="modalFormOk"></labelDrlHistory-modal>
  </a-card>
</template>

<script>
  import LabelDrlHistoryModal from './modules/LabelDrlHistoryModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "LabelDrlHistoryList",
    mixins:[JeecgListMixin],
    components: {
      LabelDrlHistoryModal
    },
    data () {
      return {
        description: '标签drl文件历史表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '描述',
            align:"center",
            dataIndex: 'name'
           },
		   {
            title: '版本号',
            align:"center",
            dataIndex: 'labelVersion'
           },
		   {
            title: '内容',
            align:"center",
            dataIndex: 'content'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/label/labelDrlHistory/list",
          delete: "/label/labelDrlHistory/delete",
          deleteBatch: "/label/labelDrlHistory/deleteBatch",
          exportXlsUrl: "label/labelDrlHistory/exportXls",
          importExcelUrl: "label/labelDrlHistory/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>