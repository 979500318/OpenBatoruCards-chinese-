package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B1_EinSangaTHEDOOR extends Card {

    public SIGNI_B1_EinSangaTHEDOOR()
    {
        setImageSets("WXDi-P16-070");

        setOriginalName("アイン＝サンガ//THE DOOR");
        setAltNames("アインサンガザドアー Ain Sanga Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：あなたのターン終了時、対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。@@を得る。\n" +
                "@U：あなたのターン終了時、あなたの場に【ゲート】がある場合、あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーをあなたの手札１枚につき＋1000する。"
        );

        setName("en", "Sanga//THE DOOR, Type: Eins");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: At the end of your turn, put target SIGNI on your opponent's field on the bottom of its owner's deck.@@@U: At the end of your turn, if there is a [[Gate]] on your field, target SIGNI on your field gets +1000 power for each card in your hand until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Ein-Sanga//THE DOOR");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: At the end of your turn, target 1 of your opponent's SIGNI, and put it on the bottom of their deck.@@" +
                "@U: At the end of your turn, if there is a [[Gate]] on your field, target 1 of your SIGNI, and until the end of your opponent's next turn, it gets +1000 power for each card in your hand."
        );

		setName("zh_simplified", "EINS=山河//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :你的回合结束时，对战对手的精灵1只作为对象，将其放置到牌组最下面。@@\n" +
                "@U :你的回合结束时，你的场上有[[大门]]的场合，你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量依据你的手牌的数量，每有1张就+1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(3000);

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
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
                
                if(target != null)
                {
                    gainPower(target, 1000 * getHandCount(getOwner()), ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
    }
}
