package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PlayerRuleValueModifier;
import open.batoru.game.gfx.GFXFieldBackground;

public final class ARTS_B_BlizzardWing extends Card {

    public ARTS_B_BlizzardWing()
    {
        setImageSets("WX25-P2-005", "WX25-P2-005U");

        setOriginalName("ブリザード・ウィング");
        setAltNames("ブリザードウィング Burizaado Uingu");
        setDescription("jp",
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@C：あなたの手札の枚数の上限は２増える。\n" +
                "@U：あなたのエナフェイズ開始時、カードを１枚引く。"
        );

        setName("en", "Blizzard Wing");
        setDescription("en",
                "During this game, you gain the following abilities:" +
                "@>@C: Your maximum hand size is increased by 2.\n" +
                "@U: At the beginning of your ener phase, draw 1 card."
        );

		setName("zh_simplified", "暴风雪·羽翼");
        setDescription("zh_simplified", 
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@C :你的手牌的张数的上限增2。（从6张变为8张）\n" +
                "@U 你的充能阶段开始时，抽1张牌。（从手牌或场上往能量区把牌放置前抽牌）@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
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

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleValueModifier(PlayerRuleValueType.MAX_HAND_SIZE, TargetFilter.HINT_OWNER_OWN, 2));
            GFXFieldBackground.attachToAbility(attachedConst, new GFXFieldBackground(getOwner(), CardLocation.LRIG, "aura_ice"));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());

            AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setNestedDescriptionOffset(1);
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ENER ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            draw(1);
        }
    }
}

