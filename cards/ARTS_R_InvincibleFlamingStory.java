package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_R_InvincibleFlamingStory extends Card {

    public ARTS_R_InvincibleFlamingStory()
    {
        setImageSets("WX24-P3-004", "WX24-P3-004U");

        setOriginalName("不敗炎話");
        setAltNames("フハイエンワ Fuhaienwa");
        setDescription("jp",
                "このアーツを使用する際、あなたのライフクロス１枚をトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%R %R %R減る。\n\n" +
                "&E４枚以上@0このターン、あなたはゲームに敗北しない。"
        );

        setName("en", "Invincible Flaming Story");
        setDescription("en",
                "While using this ARTS, you may put 1 of your life cloth into the trash. If you do, the use cost of this ARTS is reduced by %R %R %R.\n\n" +
                "&E4 or more@0 This turn, you can't lose the game."
        );

        setName("zh_simplified", "不败炎话");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以把你的生命护甲1张放置到废弃区。这样做的场合，这张必杀的使用费用减%R %R %R。\n" +
                "&E4张以上@0这个回合，你不会游戏败北。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 7));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
            arts.setReductionCost(new TrashCost(CardLocation.LIFE_CLOTH), Cost.color(CardColor.RED, 3));
            arts.setRecollect(4);
        }

        private ConditionState onARTSEffCond()
        {
            return arts.isRecollectFulfilled() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onARTSEff()
        {
            if(arts.isRecollectFulfilled())
            {
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXZoneWall(getOwner(),CardLocation.LIFE_CLOTH, "generic", new int[]{205,50,50}));
                
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_LOSE_GAME, getOwner(), record, data -> RuleCheckState.BLOCK);
            }
        }
    }
}
