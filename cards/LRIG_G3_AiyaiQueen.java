package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_AiyaiQueen extends Card {

    public LRIG_G3_AiyaiQueen()
    {
        setImageSets("WX25-P2-026", "WX25-P2-026U");

        setOriginalName("アイヤイ★クイーン");
        setAltNames("アイヤイクイーン Aiyai Kuiin Aiyai Queen");
        setDescription("jp",
                "@U $TO：あなたの効果１つによって＜遊具＞のシグニが合計１体以上場に出たとき、対戦相手のシグニ１体を対象とし、%G %Xを支払ってもよい。そうした場合、それをエナゾーンに置く。\n" +
                "@A $G1 @[@|サーカス|@]@ %G0：あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。あなたのライフクロスをすべて見て、その中からカード１枚を場に出すかエナゾーンに置き、残りをシャッフルする。"
        );

        setName("en", "Aiyai★Queen");
        setDescription("en",
                "@U $TO: When 1 or more <<Playground Equipment>> SIGNI enter the field by one of your effects, target 1 of your opponent's SIGNI, and you may pay %G %X. If you do, put it into the ener zone.\n" +
                "@A $G1 @[@|Circus|@]@ %G0: Shuffle your deck, and add the top card of your deck to life cloth. Look at all cards in your life cloth, put 1 card from among them onto the field or put it into the ener zone, and shuffle the rest into your life cloth."
        );

		setName("zh_simplified", "艾娅伊★皇后");
        setDescription("zh_simplified", 
                "@U $TO :当因为你的效果1个把<<遊具>>精灵合计1只以上出场时，对战对手的精灵1只作为对象，可以支付%G%X。这样做的场合，将其放置到能量区。\n" +
                "@A $G1 杂耍%G0:你的牌组洗切把最上面的牌加入生命护甲。看你的生命护甲全部，从中把1张牌出场或放置到能量区，剩下的洗切。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AIYAI);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Circus");
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSource()) && getEvent().isAtOnce(1) &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.PLAYGROUND_EQUIPMENT) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
            {
                putInEner(target);
            }
        }

        private void onActionEff()
        {
            shuffleDeck();
            addToLifeCloth(1);
            
            look(getLifeClothCount(getOwner()), CardLocation.LIFE_CLOTH);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter().own().fromLooked()).get();
            if(cardIndex != null)
            {
                if(playerChoiceAction(ActionHint.FIELD, ActionHint.ENER) == 1)
                {
                    putOnField(cardIndex);
                } else {
                    putInEner(cardIndex);
                }
            }
            
            addToLifeCloth(getCardsInLooked(getOwner()));
            shuffleLifeCloth();
        }
    }
}
