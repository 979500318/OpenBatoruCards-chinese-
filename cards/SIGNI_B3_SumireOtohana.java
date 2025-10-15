package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B3_SumireOtohana extends Card {

    public SIGNI_B3_SumireOtohana()
    {
        setImageSets("WX25-CP1-071");

        setOriginalName("乙花スミレ");
        setAltNames("オトハナスミレ Otohana Sumire");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にあるすべてのシグニが＜ブルアカ＞の場合、対戦相手の手札を１枚見ないで選び、捨てさせる。" +
                "~{{C $TP：このシグニのパワーは＋5000される。@@" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Otohana Sumire");

        setName("en_fan", "Sumire Otohana");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if all of your SIGNI are <<Blue Archive>> SIGNI, choose 1 card from your opponent's hand without looking, and discard it." +
                "~{{C $TP: This SIGNI gets +5000 power.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "乙花菫");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上的全部的精灵是<<ブルアカ>>的场合，不看对战对手的手牌选1张，舍弃。\n" +
                "~{{C$TP :这只精灵的力量+5000。@@\n" +
                "（$TP :的能力，只有在对战对手的回合期间变为有效）" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            ConstantAbility cont = registerConstantAbility(new PowerModifier(5000));
            cont.setCondition(this::onConstEffCond);
            cont.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).getValidTargetsCount() == 0)
            {
                CardIndex cardIndex = playerChoiceHand().get();
                discard(cardIndex);
            }
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
