package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneWall;

public final class LRIGA_R2_AzaelaUnbreakableShield extends Card {

    public LRIGA_R2_AzaelaUnbreakableShield()
    {
        setImageSets("WXDi-P16-041");

        setOriginalName("アザエラ「無敵の盾」");
        setAltNames("アザエラムテキノタテ Azaera Muteki no Tate");
        setDescription("jp",
                "@E @[ライフクロス１枚をトラッシュに置く]@：このターン、あなたはゲームに敗北しない。"
        );

        setName("en", "Azaela \"Invincible Shield\"");
        setDescription("en",
                "@E @[Put one of your Life Cloth into your trash]@: You cannot lose the game this turn."
        );
        
        setName("en_fan", "Azaela [Unbreakable Shield]");
        setDescription("en_fan",
                "@E @[Put 1 of your life cloth into the trash]@: This turn, you can't lose the game."
        );

		setName("zh_simplified", "阿左伊来「无敌的盾」");
        setDescription("zh_simplified", 
                "@E 生命护甲1张放置到废弃区:这个回合，你不会游戏败北。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.RED);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new TrashCost(CardLocation.LIFE_CLOTH), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            GFX.attachToChronoRecord(record, new GFXZoneWall(getOwner(),CardLocation.LIFE_CLOTH, "star"));
            
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_LOSE_GAME, getOwner(), record, data -> RuleCheckState.BLOCK);
        }
    }
}
