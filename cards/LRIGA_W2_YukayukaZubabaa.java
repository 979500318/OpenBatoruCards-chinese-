package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_YukayukaZubabaa extends Card {
    
    public LRIGA_W2_YukayukaZubabaa()
    {
        setImageSets("WXDi-P05-024");
        
        setOriginalName("ゆかゆか☆ずばばー");
        setAltNames("ユカユカズババー Yukayuka Zubabaa");
        setDescription("jp",
                "@E：このターン、対戦相手はシグニを２体までしか場に出すことができない。\n" +
                "@E %W %X：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Yukayuka☆Zubaba");
        setDescription("en",
                "@E: Your opponent can only have up to two SIGNI on their field this turn. \n" +
                "@E %W %X: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "Yukayuka☆Zubabaa");
        setDescription("en_fan",
                "@E: This turn, your opponent can only have up to 2 SIGNI on the field.\n" +
                "@E %W %X: Target 1 SIGNI with @[Guard]@ from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "由香香☆哫啪啪");
        setDescription("zh_simplified", 
                "@E :这个回合，对战对手只能有精灵2只最多出场。（场上已经有3只的场合，对战对手把精灵放置到废弃区，变为2只）\n" +
                "@E %W%X从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            setPlayerRuleValue(getOpponent(), PlayerRuleValueType.MAX_ALLOWED_SIGNI, 2, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
