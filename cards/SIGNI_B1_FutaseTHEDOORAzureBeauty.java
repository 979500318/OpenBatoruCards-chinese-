package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B1_FutaseTHEDOORAzureBeauty extends Card {

    public SIGNI_B1_FutaseTHEDOORAzureBeauty()
    {
        setImageSets("WXDi-P16-069");

        setOriginalName("蒼美　ふたせ//THE DOOR");
        setAltNames("ソウビフタセザドアー Soubi Futase Za Doaa");
        setDescription("jp",
                "@U：あなたのターン終了時、対戦相手のシグニ１体を対象とし、あなたのシグニの下から＜解放派＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをデッキの一番下に置く。\n\n" +
                "@C：このカードの上にある＜解放派＞のシグニは@>@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時、それをデッキの一番下に置く。@@を得る。"
        );

        setName("en", "Futase//THE DOOR, Azure Beauty");
        setDescription("en",
                "@U: At the end of your turn, you may put a <<Liberation Division>> SIGNI underneath a SIGNI on your field into its owner's trash. If you do, put target SIGNI on your opponent's field on the bottom of its owner's deck.\n\n@C: The <<Liberation Division>> SIGNI on top of this card gains@>@U: At the beginning of your attack phase, choose target SIGNI on your opponent's field. At end of turn, put it on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Futase//THE DOOR, Azure Beauty");
        setDescription("en_fan",
                "@U: At the end of your turn, target 1 of your opponent's SIGNI, and you may put 1 <<Liberation Faction>> SIGNI from under your SIGNI into the trash. If you do, put it on the bottom of their deck.\n\n" +
                "@C: The <<Liberation Faction>> SIGNI on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and at the end of this turn, put it on the bottom of their deck."
        );

		setName("zh_simplified", "苍美 二濑//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，对战对手的精灵1只作为对象，可以从你的精灵的下面把<<解放派>>精灵1张放置到废弃区。这样做的场合，将其放置到牌组最下面。\n" +
                "@C :这张牌的上面的<<解放派>>精灵得到\n" +
                "@>@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，回合结束时，将其放置到牌组最下面。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).withUnderType(CardUnderCategory.UNDER)).get();
                
                if(trash(cardIndex))
                {
                    returnToDeck(target, DeckPosition.BOTTOM);
                }
            }
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            
            if(target != null)
            {
                int instanceId = target.getIndexedInstance().getInstanceId();
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(target.isSIGNIOnField() && target.getIndexedInstance().getInstanceId() == instanceId) returnToDeck(target, DeckPosition.BOTTOM);
                });
            }
        }
    }
}
