package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.rulechecks.CardRuleCheckData;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardSIGNIClass;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_G3_PiskiePhantomApparition extends Card {

    public SIGNI_G3_PiskiePhantomApparition()
    {
        setImageSets("WXK01-094");

        setOriginalName("幻怪　ピスキー");
        setAltNames("ゲンカイピスキー Genkai Pisukii");
        setDescription("jp",
                "@C：このシグニが中央のシグニゾーンにあるかぎり、このシグニのパワーは＋2000される。\n" +
                "@C：このシグニが右のシグニゾーンにあるかぎり、このシグニは対戦相手のシグニの効果によってバニッシュされない。"
        );

        setName("en", "Piskie, Phantom Apparition");
        setDescription("en",
                "@C: As long as this SIGNI is in the center SIGNI zone, this SIGNI gets +2000 power.\n" +
                "@C: As long as this SIGNI is in the right SIGNI zone, this SIGNI can't be banished by the effects of your opponent's SIGNI."
        );

		setName("zh_simplified", "幻怪 皮克希");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，这只精灵的力量+2000。\n" +
                "@C :这只精灵在右侧的精灵区时，这只精灵不会因为对战对手的精灵的效果破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(2000));
            ConstantAbility cont2 = registerConstantAbility(this::onConstEff2Cond, new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, this::onConstEff2ModRuleCheck));
            cont2.setOnAbilityInit(() -> GFX.attachToAbility(cont2, new GFXCardTextureLayer(cont2.getSourceCardIndex(), new GFXTextureCardCanvas("border/guard", 0.75,3))));
        }
        
        private ConditionState onConstEff1Cond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onConstEff2Cond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_RIGHT ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEff2ModRuleCheck(CardRuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) &&
                   CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
