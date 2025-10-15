package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B1_SaitouAzureGeneral extends Card {

    public SIGNI_B1_SaitouAzureGeneral()
    {
        setImageSets("WXDi-P10-059");

        setOriginalName("蒼将　サイトウ");
        setAltNames("ソウショウサイトウ Soushou Saitou");
        setDescription("jp",
                "@C：対戦相手の場に凍結状態のシグニがあるかぎり、このシグニのパワーは＋4000される。\n" +
                "@U：このシグニがバトルによって凍結状態のシグニ１体をバニッシュしたとき、カードを１枚引く。"
        );

        setName("en", "Saitou, Azure General");
        setDescription("en",
                "@C: As long as there is a frozen SIGNI on your opponent's field, this SIGNI gets +4000 power.\n" +
                "@U: Whenever this SIGNI vanishes a frozen SIGNI on your opponent's field through battle, draw a card."
        );
        
        setName("en_fan", "Saitou, Azure General");
        setDescription("en_fan",
                "@C: As long as there is a frozen SIGNI on your opponent's field, this SIGNI gets +4000 power.\n" +
                "@U: Whenever this SIGNI banishes a frozen SIGNI in battle, draw 1 card."
        );

		setName("zh_simplified", "苍将 斋藤");
        setDescription("zh_simplified", 
                "@C :对战对手的场上有冻结状态的精灵时，这只精灵的力量+4000。\n" +
                "@U :当这只精灵因为战斗把冻结状态的精灵1只破坏时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return caller.getIndexedInstance().isState(CardStateFlag.FROZEN) &&
                   getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }
    }
}
