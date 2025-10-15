package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class SIGNI_K3_HarunaKurodate extends Card {

    public SIGNI_K3_HarunaKurodate()
    {
        setImageSets("WXDi-CP02-062");

        setOriginalName("黒舘ハルナ");
        setAltNames("クロダテハルナ Kurodate Haruna");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのトラッシュに＜ブルアカ＞のカードが５枚以上ある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。１０枚以上ある場合、追加で対戦相手のデッキの上からカードを３枚トラッシュに置く。" +
                "~{{E %K：対戦相手のシグニ１体を対象とし、このターン、あなたの効果によってそれのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。"
        );

        setName("en", "Kurodate Haruna");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are five or more <<Blue Archive>> cards in your trash, target SIGNI on your opponent's field gets --5000 power until end of turn. If there are ten or more, in addition, put the top three cards of your opponent's deck into their trash.~{{E %K: If your effect would -- the power of target SIGNI on your opponent's field this turn, the amount of power that would be -- is doubled instead."
        );
        
        setName("en_fan", "Haruna Kurodate");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 5 or more <<Blue Archive>> cards in your trash, target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power. If there are 10 or more, additionally put the top 3 cards of your opponent's deck into the trash." +
                "~{{E %K: Target 1 of your opponent's SIGNI. Until end of turn, if the power of that SIGNI would be -- (minus) by your effect, it gets -- (minus) by twice as much instead."
        );

		setName("zh_simplified", "黑馆晴奈");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的废弃区的<<ブルアカ>>牌在5张以上的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。10张以上的场合，追加从对战对手的牌组上面把3张牌放置到废弃区。\n" +
                "~{{E%K:对战对手的精灵1只作为对象，这个回合，因为你的效果将其的力量-（减号）的场合，作为替代，2倍-（减号）。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

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

            EnterAbility enter = registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
            enter.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int count = new TargetFilter().own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash().getValidTargetsCount();
            
            if(count >= 5)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -5000, ChronoDuration.turnEnd());
                
                if(count >= 10)
                {
                    millDeck(getOpponent(), 3);
                }
            }
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
    }
}
