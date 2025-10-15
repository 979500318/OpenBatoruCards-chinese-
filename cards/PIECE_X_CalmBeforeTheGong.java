package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.*;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.modifiers.AttackModifier;

public final class PIECE_X_CalmBeforeTheGong extends Card {

    public PIECE_X_CalmBeforeTheGong()
    {
        setImageSets("WX25-P1-048");

        setOriginalName("Calm before the gong");
        setAltNames("カームビフォアザゴング Kaamu Bifoa Za Gongu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "このターン、あなたはレベル１以上のアシストルリグでアタックできる。"
        );

        setName("en", "Calm before the gong");
        setDescription("en",
                "=U =E with 3 or more colors among them\n\n" +
                "This turn, your level 1 or higher Assist LRIGs can attack."
        );

		setName("zh_simplified", "Calm before the gong");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "这个回合，你的等级1以上的支援分身能攻击。（在分身攻击步骤能任意顺序攻击，对战对手能对那次攻击[[防御]]）\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(
                new TargetFilter().own().anyLRIG().withLevel(1,0).fromLocation(CardLocation.LRIG_ASSIST_LEFT,CardLocation.LRIG_ASSIST_RIGHT),
                new AttackModifier(AttackModifierFlag.ASSIST_ATTACK)
            );
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
        }
    }
}
