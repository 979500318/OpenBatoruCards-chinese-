package open.batoru.data.cards;

import open.batoru.core.Deck;
import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.cost.EnerCost;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class LRIGA_K2_MachinaNebula extends Card {

    public LRIGA_K2_MachinaNebula()
    {
        setImageSets("WXDi-P05-019");

        setOriginalName("マキナネビュラ");
        setAltNames("Makina Nebyura");
        setDescription("jp",
                "@E：あなたのシグニ１体を対象とし、あなたのルリグトラッシュからルリグ１枚をそれの【ソウル】にする。\n" +
                "@E %X %X：対戦相手のシグニ１体を対象とし、あなたのトラッシュからそれぞれ共通する色を持つカード１０枚をデッキに加えてシャッフルする。そうした場合、それをバニッシュする。"
        );

        setName("en", "Machina Nebula");
        setDescription("en",
                "@E: Attach a LRIG from your LRIG Trash to target SIGNI on your field as a [[Soul]].\n" +
                "@E %X %X: Shuffle ten SIGNI that share the same color from your trash into your deck. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Machina Nebula");
        setDescription("en_fan",
                "@E: Target 1 of your SIGNI, and attach 1 LRIG from your LRIG trash to it as a [[Soul]].\n" +
                "@E %X %X: Target 1 of your opponent's SIGNI, and shuffle 10 SIGNI that share a common color from your trash to your deck. If you do, banish it."
        );

		setName("zh_simplified", "玛琪娜星云");
        setDescription("zh_simplified", 
                "@E :你的精灵1只作为对象，从你的分身废弃区把分身1张作为其的[[灵魂]]。\n" +
                "@E %X %X:对战对手的精灵1只作为对象，从你的废弃区把持有共通颜色的10张牌加入牌组洗切。这样做的场合，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().SIGNI().attachable(GameConst.CardUnderType.ATTACHED_SOUL)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().anyLRIG().fromTrash(Deck.DeckType.LRIG)).get();
                
                attach(target, cardIndex, GameConst.CardUnderType.ATTACHED_SOUL);
            }
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null)
            {
                TargetFilter filter = new TargetFilter(TargetHint.SHUFFLE).own().fromTrash();
                DataTable<CardIndex> data = filter.getExportedData();
                
                if(data.get() != null)
                {
                    Set<CardColor> cacheColors = new HashSet<>();
                    
                    for(int i=0,found=0;i<data.size();i++)
                    {
                        CardDataColor dataColor = data.get(i).getIndexedInstance().getColor();
                        
                        if(!cacheColors.isEmpty() && !dataColor.matches(cacheColors)) continue;
                        
                        if(++found == 10)
                        {
                            DataTable<CardIndex> dataShuffled = playerTargetCard(10, filter, this::onEnterEff2TargetCond);

                            if(returnToDeck(dataShuffled, DeckPosition.TOP) == 10 && shuffleDeck())
                            {
                                banish(target);
                            }
                            break;
                        }
                        
                        cacheColors.addAll(dataColor.getValue());
                    }
                }
            }
        }
        private boolean onEnterEff2TargetCond(List<CardIndex> listPickedCards)
        {
            if(listPickedCards.size() < 10) return false;

            CardDataColor cacheColors = listPickedCards.get(0).getIndexedInstance().getColor();
            for(int i=1;i<listPickedCards.size();i++) if(!listPickedCards.get(i).getIndexedInstance().getColor().matches(cacheColors)) return false;

            return true;
        }
    }
}
