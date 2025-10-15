package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIGA_W2_SasheBaladi extends Card {

    public LRIGA_W2_SasheBaladi()
    {
        setImageSets("WXDi-P13-029");

        setOriginalName("サシェ・バラディ");
        setAltNames("サシェバラディ Sashe Baradi");
        setDescription("jp",
                "@E：次の対戦相手のメインフェイズの間、対戦相手のセンタールリグのリミットを－２する。\n" +
                "@E：対戦相手のレベル２以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Sashe Baladi");
        setDescription("en",
                "@E: During your opponent's next main phase, decrease their Center LRIG's limit by two.\n@E: Return target level two or less SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Sashe Baladi");
        setDescription("en_fan",
                "@E: During your opponent's next main phase, your opponent's center LRIG gets --2 limit.\n" +
                "@E: Target 1 of your opponent's level 2 or lower SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "莎榭·肚皮舞");
        setDescription("zh_simplified", 
                "@E :下一个对战对手的主要阶段期间，对战对手的核心分身的界限-2。\n" +
                "@E :对战对手的等级2以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SASHE);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().LRIG(),
                new ModifiableValueModifier<>(this::onAttachedConstEffModGetSample, () -> -2d)
            );
            attachedConst.setCondition(this::onAttachedConstEffCond);

            GFX.attachToAbility(attachedConst, new GFXZoneUnderIndicator(getOpponent(),CardLocation.LRIG, "limit_down"));
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextPhaseEnd(getOpponent(), GamePhase.MAIN));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLimit();
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,2)).get();
            addToHand(target);
        }
    }
}
