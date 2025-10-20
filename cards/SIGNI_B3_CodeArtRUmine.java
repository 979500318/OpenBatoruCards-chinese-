package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_CodeArtRUmine extends Card {

    public SIGNI_B3_CodeArtRUmine()
    {
        setImageSets("WX24-D3-20");

        setOriginalName("コードハート　Ｒミネ");
        setAltNames("コードハートアールミネ Koodo Aato Aa Rumine");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。&E５枚以上@0代わりに対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~#どちらか１つを選ぶ。\n$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n$$2カードを１枚引く。"
        );

        setName("en", "Code Art R Umine");
        setDescription("en",
                "@U: At the beginning of your attack phase, your opponent discards 1 card from their hand. &E5 or more@0 Instead, choose 1 card from your opponent's hand without looking, and discard it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "爱心代号 扫地机");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，对战对手把手牌1张舍弃。&E5张以上@0作为替代，不看对战对手的手牌选1张，舍弃。\n" +
                "（你的分身废弃区有5张以上的必杀时，则&E5张以上@0后的文字变为有效）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setRecollect(5);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!getAbility().isRecollectFulfilled())
            {
                discard(getOpponent(), 1);
            } else {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
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
