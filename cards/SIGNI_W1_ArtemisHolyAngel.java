package open.batoru.data.cards;

import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_W1_ArtemisHolyAngel extends Card {

    public SIGNI_W1_ArtemisHolyAngel()
    {
        setImageSets("WXDi-P10-046", "SPDi38-11");

        setOriginalName("聖天　アルテミス");
        setAltNames("セイテンアルテミス Seiten Arutemisu");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニは対戦相手のレベル２以下の、ルリグとシグニの効果によってバニッシュされない。"
        );

        setName("en", "Artemis, Blessed Angel");
        setDescription("en",
                "@C: During your opponent's turn, this SIGNI cannot be vanished by the effects of your opponent's LRIG and SIGNI that are level two or less. "
        );
        
        setName("en_fan", "Artemis, Holy Angel");
        setDescription("en_fan",
                "@C: During your opponent's turn, this SIGNI can't be banished by the effects of your opponent's level 2 or lower LRIG and SIGNI."
        );

		setName("zh_simplified", "圣天 阿耳忒弥斯");
        setDescription("zh_simplified", 
                "@C $TP :这只精灵不会因为对战对手的等级2以下的、分身和精灵的效果被破坏。（与精灵战斗或力量在0以下的场合，会被破坏）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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

            registerConstantAbility(this::onConstEffCond, new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onConstEffModRuleCheck));
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) &&
                   (CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) || CardType.isLRIG(data.getSourceCardIndex().getCardReference().getType())) &&
                    data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() <= 2 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
