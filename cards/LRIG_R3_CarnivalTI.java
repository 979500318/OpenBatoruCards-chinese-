package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_R3_CarnivalTI extends Card {

    public LRIG_R3_CarnivalTI()
    {
        setImageSets("WXDi-P09-005", "WXDi-P09-005U");
        setLinkedImageSets("WXDi-P07-TK01-A");

        setOriginalName("カーニバル　―TI―");
        setAltNames("カーニバルティタニア Kaanibaru Titania Carnival TI");
        setDescription("jp",
                "@A $T1 @[手札を１枚捨てる]@：ターン終了時まで、このルリグは@>@U $T1：このルリグがアタックしたとき、以下の３つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2【エナチャージ１】\n" +
                "$$3対戦相手のシグニ１体を対象とし、それをトラッシュに置く。@@を得る。\n" +
                "@A $G1 %R0：対戦相手のシグニを２体まで対象とし、ターン終了時まで、それらを《サーバント ＺＥＲＯ》にする。"
        );

        setName("en", "Carnival -TI-");
        setDescription("en",
                "@A $T1 @[Discard a card]@: This LRIG gains" +
                "@>@U $T1: When this LRIG attacks, choose one of the following.\n" +
                "$$1 Draw a card.\n" +
                "$$2 [[Ener Charge 1]].\n" +
                "$$3 Put target SIGNI on your opponent's field into its owner's trash.@@until end of turn.\n" +
                "@A $G1 %R0: Make up to two target SIGNI on your opponent's field \"Servant ZERO\" until end of turn."
        );
        
        setName("en_fan", "Carnival -TI-");
        setDescription("en_fan",
                "@A $T1 @[Discard 1 card from your hand]@: Until end of turn, this LRIG gains:" +
                "@>@U $T1: When this LRIG attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 [[Ener Charge 1]]\n" +
                "$$3 Target 1 of your opponent's SIGNI, and put it into the trash.@@" +
                "@A $G1 %R0: Target up to 2 of your opponent's SIGNI, and until end of turn, they become \"Servant ZERO\"."
        );

		setName("zh_simplified", "嘉年华 -TI-");
        setDescription("zh_simplified", 
                "@A $T1 手牌1张舍弃:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 :当这只分身攻击时，从以下的3种选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 [[能量填充1]]\n" +
                "$$3 对战对手的精灵1只作为对象，将其放置到废弃区。@@\n" +
                "@A $G1 %R0:对战对手的精灵2只最多作为对象，直到回合结束时为止，这些变为《サーバント　ＺＥＲＯ》。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);
        setCoins(+2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new DiscardCost(1), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onActionEff1()
        {
            AutoAbility attachedAuto = new AutoAbility(GameConst.GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            switch(playerChoiceMode())
            {
                case 1 -> draw(1);
                case 1<<1 -> enerCharge(1);
                case 1<<2 -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                    trash(target);
                }
            }
        }
        
        private void onActionEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.TRANSFORM).OP().SIGNI());
            for(int i=0;i<data.size();i++) transform(data.get(i), getLinkedImageSets().get(0), ChronoDuration.turnEnd());
        }
    }
}
