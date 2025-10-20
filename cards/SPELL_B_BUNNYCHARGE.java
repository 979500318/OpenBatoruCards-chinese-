package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.Enter;

public final class SPELL_B_BUNNYCHARGE extends Card {

    public SPELL_B_BUNNYCHARGE()
    {
        setImageSets("WX24-P2-080");

        setOriginalName("BUNNY CHARGE");
        setAltNames("バニーチャージ Banii Chaaji");
        setDescription("jp",
                "カードを２枚引く。手札を１枚捨ててもよい。そうした場合、このターンの次のアタックフェイズ終了時、あなたの手札からレベル２以下の＜遊具＞のシグニ１枚を場に出してもよい。そのシグニの@E能力は発動しない。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "BUNNY CHARGE");
        setDescription("en",
                "Draw 2 cards. You may discard 1 card from your hand. If you do, at the end of your next attack phase this turn, you may put 1 level 2 or lower <<Playground Equipment>> SIGNI from your hand onto the field. That SIGNI's @E abilities don't activate." +
                "~#Target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "BUNNY CHARGE");
        setDescription("zh_simplified", 
                "抽2张牌。可以把手牌1张舍弃。这样做的场合，这个回合的下一个攻击阶段结束时，可以从你的手牌把等级2以下的<<遊具>>精灵1张出场。那只精灵的@E能力不能发动。" +
                "~#对战对手的精灵2只最多作为对象，将这些横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            draw(2);
            
            if(discard(0,1).get() != null)
            {
                callDelayedEffect(ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ATTACK_LRIG), () -> {
                    CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).withLevel(0,2).fromHand().playable()).get();
                    putOnField(target, Enter.DONT_ACTIVATE);
                });
            }
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}
