package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class SIGNI_W2_CodeAntiElena extends Card {
    
    public SIGNI_W2_CodeAntiElena()
    {
        setImageSets("WXDi-P03-052");
        
        setOriginalName("コードアンチ　エレナ");
        setAltNames("コードアンチエレナ Koodo Anchi Erena");
        setDescription("jp",
                "@C：すべてのプレイヤーは白ではないスペルを使用できない。"
        );
        
        setName("en", "Elena, Code: Anti");
        setDescription("en",
                "@C: Players cannot use non-white spells."
        );
        
        setName("en_fan", "Code Anti Elena");
        setDescription("en_fan",
                "@C: All players can't use non-white spells."
        );
        
		setName("zh_simplified", "古兵代号 埃琳娜");
        setDescription("zh_simplified", 
                "@C :全部的玩家不能把不是白色的魔法使用。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_SPELL,
                TargetFilter.HINT_OWNER_OWN | TargetFilter.HINT_OWNER_OP, this::onConstEffModRuleCheck)
            );
        }
        
        private RuleCheckState onConstEffModRuleCheck(RuleCheckData data)
        {
            return !data.getSourceCardIndex().getIndexedInstance().getColor().matches(CardColor.WHITE) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
