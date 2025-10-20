package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_NoaUshio extends Card {

    public SIGNI_B3_NoaUshio()
    {
        setImageSets("WXDi-CP02-082");

        setOriginalName("生塩ノア");
        setAltNames("ウシオノア Ushio Noa");
        setDescription("jp",
                "@U $T1：あなたのターンの間、コストか効果によってあなたが手札から＜ブルアカ＞のカードを１枚捨てたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。" +
                "~{{U $T1：対戦相手のターンの間、あなたが手札から＜ブルアカ＞のカードを１枚捨てたとき、カードを１枚引く。@@" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Ushio Noa");
        setDescription("en",
                "@U $T1: During your turn, when you discard a <<Blue Archive>> card by a cost or an effect, target SIGNI on your opponent's field gets --3000 power until end of turn.~{{U $T1: During your opponent's turn, when you discard a <<Blue Archive>> card, draw a card.@@" +
                "~#Choose one -- \n$$1Down up to two target SIGNI on your opponent's field. \n$$2Draw a card."
        );
        
        setName("en_fan", "Noa Ushio");
        setDescription("en_fan",
                "@U $T1: During your turn, when you discard a <<Blue Archive>> card from your hand by a cost or an effect, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power." +
                "~{{U $T1: During your opponent's turn, when you discard a <<Blue Archive>> card from your hand, draw 1 card.@@" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "生盐乃爱");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当因为费用或效果你从手牌把<<ブルアカ>>牌1张舍弃时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n" +
                "~{{U$T1 :对战对手的回合期间，当你从手牌把<<ブルアカ>>牌1张舍弃时，抽1张牌。@@" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) &&
                   getEvent().getSourceAbility() != null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnTurn() && isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
