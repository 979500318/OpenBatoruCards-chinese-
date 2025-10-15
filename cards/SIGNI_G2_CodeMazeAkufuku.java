package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_CodeMazeAkufuku extends Card {
    
    public SIGNI_G2_CodeMazeAkufuku()
    {
        setImageSets("WXDi-P03-077", "SPDi01-62");
        
        setOriginalName("コードメイズ　アクフク");
        setAltNames("コードメイズアクフク Koodo Meizu Akufuku");
        setDescription("jp",
                "~#：[[エナチャージ１]]をする。このターン、あなたは対戦相手のレベル３以下のシグニによってダメージを受けない。"
        );
        
        setName("en", "Akufuku, Code: Maze");
        setDescription("en",
                "~#[[Ener Charge 1]]. You do not take damage from your opponent's level three or less SIGNI this turn."
        );
        
        setName("en_fan", "Code Maze Akufuku");
        setDescription("en_fan",
                "~#[[Ener Charge 1]]. This turn, you can't be damaged by your opponent's level 3 or lower SIGNI."
        );
        
		setName("zh_simplified", "迷宫代号 阿库罗斯福冈");
        setDescription("zh_simplified", 
                "~#[[能量填充1]]。这个回合，你不会因为对战对手的等级3以下的精灵受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_DAMAGED, getOwner(), ChronoDuration.turnEnd(), data -> {
                return !isOwnCard(data.getSourceCardIndex()) &&
                        CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                        data.getSourceCardIndex().getIndexedInstance().getLevel().getValue() <= 3 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
            });
        }
    }
}
