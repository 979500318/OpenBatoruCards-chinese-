package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class SIGNI_K2_HigurashiPhantomInsect extends Card {

    public SIGNI_K2_HigurashiPhantomInsect()
    {
        setImageSets("WX25-P2-103");

        setOriginalName("幻蟲　ヒグラシ");
        setAltNames("ゲンチュウヒグラシ Genchuu Higurashi");
        setDescription("jp",
                "@E @[エナゾーンから＜凶蟲＞のシグニ１枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、以下の２つから１つを選ぶ。\n" +
                "$$1このターン、あなたの効果によってそれのパワーが－（マイナス）される場合、代わりにその２倍－（マイナス）される。\n" +
                "$$2それに【チャーム】が付いている場合、このターン、あなたの効果によってそれのパワーが－される場合、代わりに３倍－される。" +
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );

        setName("en", "Evening Cicada, Phantom Insect");
        setDescription("en",
                "@E @[Put 1 <<Misfortune Insect>> SIGNI from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and @[@|choose 1 of the following:|@]@\n" +
                "$$1 This, if its power would be -- (minus) by your effect, it gets -- (minus) by twice as much instead.\n" +
                "$$2 If it has a [[Charm]] attached to it, this turn, if its power would be -- (minus) by your effect, it gets -- (minus) by thrice as much instead." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --12000 power."
        );

        setName("en_fan", "Higurashi, Phantom Insect");

		setName("zh_simplified", "幻虫 日本暮蝉");
        setDescription("zh_simplified", 
                "@E 从能量区把<<凶蟲>>精灵1张放置到废弃区:对战对手的精灵1只作为对象，从以下的2种选1种。\n" +
                "$$1 这个回合，因为你的效果将其的力量-（减号）的场合，作为替代，2倍-（减号）。\n" +
                "$$2 其有[[魅饰]]附加的场合，这个回合，因为你的效果将其的力量-的场合，作为替代，3倍-。" +
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
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

            registerEnterAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT).fromEner()), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                int multi;
                if(playerChoiceMode() == 1) multi = 2;
                else if(target.getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_CHARM) > 0) multi = 3;
                else return;
                
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(target),
                    new ModifiableAddedValueModifier<>(this::onAttachedConstEffModGetSample, (mod, addedValue) ->
                        addedValue < 0 && isOwnCard(mod.getSourceAbility().getSourceCardIndex()) ? addedValue * multi : addedValue
                    )
                );
                GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, multi == 2 ? "double_minus" : "triple_minus"));
                attachedConst.setForcedModifierUpdate(mod -> mod instanceof PowerModifier);
                
                attachPlayerAbility(getOwner(), attachedConst, record);
            }
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
