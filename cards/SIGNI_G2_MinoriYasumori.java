package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_MinoriYasumori extends Card {

    public SIGNI_G2_MinoriYasumori()
    {
        setImageSets("WX25-CP1-080");

        setOriginalName("安守ミノリ");
        setAltNames("ヤスモリミノリ Yasumori Minori");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのエナゾーンから＜ブルアカ＞のカードを４枚までトラッシュに置いてもよい。その後、パワーが「この方法でトラッシュに置いたカードの枚数×4000」以下の対戦相手のシグニ１体を対象とし、それをバニッシュする。" +
                "~{{U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、【エナチャージ１】をする。"
        );

        setName("en", "Yasumori Minori");

        setName("en_fan", "Minori Yasumori");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put up to 4 <<Blue Archive>> cards from your ener zone into the trash. Then, target 1 of your opponent's SIGNI with power \"number of cards put into the trash this way x 4000\" or less, and banish it." +
                "~{{U $T1: When this SIGNI crushes 1 of your opponent's life cloth, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "安守实里");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从你的能量区把<<ブルアカ>>牌4张最多放置到废弃区。然后，力量在\n" +
                "@>:这个方法放置到废弃区的牌的张数x4000@@\n" +
                "以下的对战对手的精灵1只作为对象，将其破坏。\n" +
                "~{{U$T1 :当这只精灵把对战对手的生命护甲1张击溃时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,4, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
            int numTrashed = trash(data);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,numTrashed * 4000)).get();
            banish(target);
        }

        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
