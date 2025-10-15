package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B2_BangTHEDOORAzureDevil extends Card {

    public SIGNI_B2_BangTHEDOORAzureDevil()
    {
        setImageSets("WXDi-P15-082");

        setOriginalName("蒼魔　バン//THE DOOR");
        setAltNames("ソウマバンザドアー Souma Ban Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：あなたのアタックフェイズ開始時、対戦相手は手札を１枚捨てる。@@を得る。\n" +
                "@U：あなたのターン終了時、【ゲート】があるあなたのシグニゾーンの正面にある対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Bang//THE DOOR, Azure Evil");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: At the beginning of your attack phase, your opponent discards a card.@@@U: At the end of your turn, put target SIGNI on your opponent's field that is in front of one of your SIGNI Zones with a [[Gate]] on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Bang//THE DOOR, Azure Devil");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: At the beginning of your attack phase, your opponent discards 1 card from their hand.@@" +
                "@U: At the end of your turn, target 1 of your opponent's SIGNI in front of 1 of your SIGNI zones with a [[Gate]], and put it on the bottom of their deck."
        );

		setName("zh_simplified", "苍魔 梆//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :你的攻击阶段开始时，对战对手把手牌1张舍弃。@@\n" +
                "@U :你的回合结束时，有[[大门]]的你的精灵区的正面的对战对手的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = new DataTable<>();
            forEachSIGNIOnField(getOpponent(), cardIndex -> {
                if(new TargetFilter().own().SIGNI().zone().fromLocation(CardLocation.getOppositeSIGNILocation(cardIndex.getLocation())).withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
                {
                    data.add(cardIndex);
                }
            });
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().match(data)).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
