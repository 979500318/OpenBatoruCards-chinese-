package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K2_MutsukiAsagi extends Card {

    public SIGNI_K2_MutsukiAsagi()
    {
        setImageSets("WXDi-CP02-101");
        
        setOriginalName("浅黄ムツキ");
        setAltNames("アサギムツキ Asagi Mutsuki");
        setDescription("jp",
                "@E @[エナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置く]@：あなたのデッキの上からカードを３枚トラッシュに置く。その後、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをこの方法でデッキからトラッシュに置かれた＜ブルアカ＞のシグニのレベルの合計１につき－1000する。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Asagi Mutsuki");
        setDescription("en",
                "@E @[Put a <<Blue Archive>> card from your Ener Zone into your trash]@: Put the top three cards of your deck into your trash. Then, target SIGNI on your opponent's field gets --1000 power for every level of each <<Blue Archive>> SIGNI put from your deck into your trash this way until end of turn.~{{C: This SIGNI gets +4000 power."
        );
        
        setName("en_fan", "Mutsuki Asagi");
        setDescription("en_fan",
                "@E @[Put 1 <<Blue Archive>> card from your ener zone into the trash]@: Put the top 3 cards of your deck into the trash. Then, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each level of every <<Blue Archive>> SIGNI put into the trash this way." +
                "~{{C: This SIGNI gets +4000 power."
        );
        
		setName("zh_simplified", "浅黄睦月");
        setDescription("zh_simplified", 
                "@E 从能量区把<<ブルアカ>>牌1张放置到废弃区:从你的牌组上面把3张牌放置到废弃区。然后，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据这个方法从牌组放置到废弃区的<<ブルアカ>>精灵的等级的合计的数量，每有1级就-1000。\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
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
            
            registerEnterAbility(new TrashCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()), this::onEnterEff);
            
            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = millDeck(3);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                int sum = 0;
                for(int i=0;i<data.size();i++)
                {
                    CardIndex cardIndex = data.get(i);
                    if(cardIndex == null) break;
                    
                    if(cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) &&
                       CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                    {
                        sum += cardIndex.getIndexedInstance().getLevelByRef();
                    }
                }
                
                gainPower(target, -1000 * sum, ChronoDuration.turnEnd());
            }
        }
    }
}

