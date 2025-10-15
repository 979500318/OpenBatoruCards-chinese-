package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_MamaDefense extends Card {

    public LRIGA_G2_MamaDefense()
    {
        setImageSets("WXDi-P14-032");

        setOriginalName("ママ♥ディフェンス");
        setAltNames("ママディフェンス Mama Difensu");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。\n" +
                "@E %G %X：対戦相手のセンタールリグ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。\n" +
                "@E %X %X %X：対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。"
        );

        setName("en", "Mama ♥ Defense");
        setDescription("en",
                "@E: When target SIGNI on your opponent's field attacks next this turn, negate the attack.\n@E %G %X: When target Center LRIG on your opponent's field attacks next this turn, negate the attack.\n@E %X %X %X: When target SIGNI on your opponent's field attacks next this turn, negate the attack."
        );
        
        setName("en_fan", "Mama♥Defense");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI. This turn, the next time it attacks, disable that attack.\n" +
                "@E %G %X: Target your opponent's center LRIG. This turn, the next time it attacks, disable that attack.\n" +
                "@E %X %X %X: Target 1 of your opponent's SIGNI. This turn, the next time it attacks, disable that attack."
        );

		setName("zh_simplified", "妈妈♥防御");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n" +
                "@E %G%X:对战对手的核心分身1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n" +
                "@E %X %X %X:对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAMA);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private int number;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onEnterEff2);
            registerEnterAbility(new EnerCost(Cost.colorless(3)), this::onEnterEff1);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            disableNextAttack(target);
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().LRIG()).get();
            disableNextAttack(target);
        }
    }
}
