package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K1_MeguShimokura extends Card {

    public SIGNI_K1_MeguShimokura()
    {
        setImageSets("WX25-CP1-086");

        setOriginalName("下倉メグ");
        setAltNames("シモクラメグ Shimokura Megu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に他の＜ブルアカ＞のシグニがある場合、対戦相手のデッキの上からカードを２枚トラッシュに置く。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のデッキの上からカードを２枚トラッシュに置く。@@" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Shimokura Megu");

        setName("en_fan", "Megu Shimokura");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<Blue Archive>> SIGNI on your field, put the top 2 cards of your opponent's deck into the trash." +
                "~{{U: At the beginning of your attack phase, put the top 2 cards of your opponent's deck into the trash.@@" +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "下仓惠");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<ブルアカ>>精灵的场合，从对战对手的牌组上面把2张牌放置到废弃区。\n" +
                "~{{U:你的攻击阶段开始时，从对战对手的牌组上面把2张牌放置到废弃区。@@" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

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
            auto1.setCondition(this::onAutoEffCond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                millDeck(getOpponent(), 2);
            }
        }

        private void onAutoEff2(CardIndex caller)
        {
            millDeck(getOpponent(), 2);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
