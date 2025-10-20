package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class SIGNI_B2_MiyuKasumizawa extends Card {

    public SIGNI_B2_MiyuKasumizawa()
    {
        setImageSets("WX25-CP1-070");

        setOriginalName("霞沢ミユ");
        setAltNames("カスミザワミユ Kasumizawa Miyu");
        setDescription("jp",
                "@E @[手札から＜ブルアカ＞のカードを１枚捨てる]@：対戦相手のシグニ１体を対象とし、このターン、あなたの効果によってそれのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。" +
                "~{{U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。"
        );

        setName("en", "Kasumizawa Miyu");

        setName("en_fan", "Miyu Kasumizawa");
        setDescription("en",
                "@E @[Discard 1 <<Blue Archive>> card from your hand]@: Target 1 of your opponent's SIGNI, and this turn, if its power would be -- (minus) by your effect, it gets -- (minus) by twice as much instead." +
                "~{{U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, draw 1 card."
        );

		setName("zh_simplified", "霞泽美游");
        setDescription("zh_simplified", 
                "@E 从手牌把<<ブルアカ>>牌1张舍弃:对战对手的精灵1只作为对象，这个回合，因为你的效果将其的力量-（减号）的场合，作为替代，2倍-（减号）。\n" +
                "~{{U:你的攻击阶段开始时，可以把竖直状态的这只精灵横置。这样做的场合，抽1张牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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
            
            registerEnterAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().match(target),
                    new ModifiableAddedValueModifier<>(this::onAttachedConstEffModGetSample, this::onAttachedConstEffModAddedValue)
                );
                GFXCardTextureLayer.attachToChronoRecord(record, new GFXCardTextureLayer(target, "double_minus"));
                attachedConst.setForcedModifierUpdate(mod -> mod instanceof PowerModifier);
                
                attachPlayerAbility(getOwner(), attachedConst, record);
            }
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        private double onAttachedConstEffModAddedValue(ModifiableDouble mod, double addedValue)
        {
            return addedValue < 0 && isOwnCard(mod.getSourceAbility().getSourceCardIndex()) ? addedValue * 2 : addedValue;
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                draw(1);
            }
        }
    }
}
