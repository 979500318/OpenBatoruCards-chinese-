package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_B3_CostumeTHEDOORNaturalStarPrincess extends Card {

    public SIGNI_B3_CostumeTHEDOORNaturalStarPrincess()
    {
        setImageSets("WXDi-P15-058");
        setLinkedImageSets("WXDi-P15-011");

        setOriginalName("羅星姫　コスチュム//THE DOOR");
        setAltNames("ラセイキコスチュムザドアー Raseiki Kosuchumu Za Doaa");
        setDescription("jp",
                "@C：同じシグニゾーンに【ゲート】があるあなたのシグニは[[シャドウ（スペル）]]を得る。\n" +
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@U：あなたのアタックフェイズ開始時、あなたの場に《プロフェッサー　防衛者Ｄｒ．タマゴ》がいる場合、対戦相手のシグニ１体を対象とし、%B %Bを支払ってもよい。そうした場合、それをデッキの一番下に置く。@@を得る。\n" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Costume//THE DOOR, Galactic Queen");
        setDescription("en",
                "@C: SIGNI on your field in the same SIGNI Zone as a [[Gate]] gain [[Shadow -- Spell]].\n@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@U: At the beginning of your attack phase, if \"Professor! Defender Dr. Tamago\" is on your field, you may pay %B %B. If you do, put target SIGNI on your opponent's field on the bottom of its owner's deck.@@" +
                "~#Put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Costume//THE DOOR, Natural Star Princess");
        setDescription("en_fan",
                "@C: All of your SIGNI on SIGNI zones with a [[Gate]] gain [[Shadow (spell)]].\n" +
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@U: At the beginning of your attack phase, if your LRIG is \"Defender Dr. Tamago, the Professor\", target 1 of your opponent's SIGNI, and you may pay %B %B. If you do, put it on the bottom of their deck.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "罗星姬 服化道//THE DOOR");
        setDescription("zh_simplified", 
                "@C :相同精灵区有[[大门]]的你的精灵得到[[暗影（魔法）]]。\n" +
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@U :你的攻击阶段开始时，你的场上有《プロフェッサー　防衛者Ｄｒ．タマゴ》的场合，对战对手的精灵1只作为对象，可以支付%B %B。这样做的场合，将其放置到牌组最下面。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().SIGNI().withZoneObject(CardUnderType.ZONE_GATE), new AbilityGainModifier(this::onConstEff1ModGetSample));

            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private Ability onConstEff1ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getCardReference().getType() == CardType.SPELL ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
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
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("プロフェッサー　防衛者Ｄｒ．タマゴ"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLUE, 2)))
                {
                    returnToDeck(target, DeckPosition.BOTTOM);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
