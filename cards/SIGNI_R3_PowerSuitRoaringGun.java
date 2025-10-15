package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.EventTarget;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_R3_PowerSuitRoaringGun extends Card {

    public SIGNI_R3_PowerSuitRoaringGun()
    {
        setImageSets("WX25-P2-055");

        setOriginalName("轟砲　パワードスーツ");
        setAltNames("ゴウホウパワードスーツ Gouhou Pawaado Suutsu");
        setDescription("jp",
                "@C：このシグニはバニッシュされない。\n" +
                "@U $T1：このシグニが対戦相手の、能力か効果の対象になったとき、ターン終了時まで、このシグニは常 能力を失う。" +
                "~#どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Power Suit, Roaring Gun");
        setDescription("en",
                "@C: This SIGNI can't be banished.\n" +
                "@U $T1: When this SIGNI is targeted by your opponent's ability or effect, until end of turn, this SIGNI loses its @C abilities." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "轰炮 动力装甲");
        setDescription("zh_simplified", 
                "@C :这只精灵不会被破坏。\n" +
                "@U $T1 当这只精灵被作为对战对手的，能力或效果的对象时，直到回合结束时为止，这只精灵的@C能力失去。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ConstantAbility cont = registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, data -> RuleCheckState.BLOCK));
            cont.setOnAbilityInit(() -> GFX.attachToAbility(cont, new GFXCardTextureLayer(cont.getSourceCardIndex(), new GFXTextureCardCanvas("border/guard", 0.75,3))));

            AutoAbility auto = registerAutoAbility(GameEventId.TARGET, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getCardIndex().isSIGNIOnField())
            {
                disableAllAbilities(getCardIndex(), ability -> ability instanceof ConstantAbility, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
