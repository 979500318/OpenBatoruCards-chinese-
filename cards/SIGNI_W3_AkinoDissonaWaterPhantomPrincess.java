package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.actions.ActionAddToHand;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_W3_AkinoDissonaWaterPhantomPrincess extends Card {

    public SIGNI_W3_AkinoDissonaWaterPhantomPrincess()
    {
        setImageSets("WXDi-P13-045", "WXDi-P13-045P");

        setOriginalName("幻水姫　アキノ//ディソナ");
        setAltNames("ゲンスイヒメアキノディソナ Gensuihime Akino Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー8000以下のシグニ１体を対象とし、手札から#Sのカードを１枚捨ててもよい。そうした場合、それを手札に戻す。\n" +
                "$$2対戦相手のシグニ１体を対象とし、手札から#Gを持つシグニを１枚捨ててもよい。そうした場合、それを手札に戻す。\n" +
                "@A %X：このターン、対戦相手のシグニがバニッシュされる場合、エナゾーンに置かれる代わりに手札に戻される。"
        );

        setName("en", "Akino//Dissona, Aquatic Phantom Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, choose one of the following.\n$$1You may discard a #S card. If you do, return target SIGNI on your opponent's field with power 8000 or less to its owner's hand.\n$$2You may discard a SIGNI with a #G. If you do, return target SIGNI on your opponent's field to its owner's hand.\n@A %X: If a SIGNI on your opponent's field is vanished this turn, it is returned to its owner's hand instead of putting into the Ener Zone."
        );
        
        setName("en_fan", "Akino//Dissona, Water Phantom Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 8000 or less, and you may discard 1 #S @[Dissona]@ card from your hand. If you do, return it to their hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and you may discard 1 #G @[Guard]@ SIGNI from your hand. If you do, return it to their hand.\n" +
                "@A %X: This turn, whenever 1 of your opponent's SIGNI would be banished, it is returned to their hand instead of being put in the ener zone."
        );

		setName("zh_simplified", "幻水姬 昭乃//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 对战对手的力量8000以下的精灵1只作为对象，可以从手牌把#S的牌1张舍弃。这样做的场合，将其返回手牌。\n" +
                "$$2 对战对手的精灵1只作为对象，可以从手牌把持有#G的精灵1张舍弃。这样做的场合，将其返回手牌。\n" +
                "@A %X:这个回合，对战对手的精灵被破坏的场合，放置到能量区，作为替代，返回手牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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

            registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
                
                if(target != null && discard(0,1, new TargetFilter().dissona()).get() != null)
                {
                    addToHand(target);
                }
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                
                if(target != null && discard(0,1, new TargetFilter().withState(CardStateFlag.CAN_GUARD)).get() != null)
                {
                    addToHand(target);
                }
            }
        }
        
        private void onActionEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                    new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER,OverrideFlag.MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideHandler)
                )
            );
            GFX.attachToSharedAbility(attachedConst, cardIndex -> new GFXCardTextureLayer(cardIndex, new GFXTextureCardCanvas("border/hand", 0.75,3)));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionAddToHand(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}

