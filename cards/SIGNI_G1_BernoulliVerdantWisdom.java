package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_G1_BernoulliVerdantWisdom extends Card {

    public SIGNI_G1_BernoulliVerdantWisdom()
    {
        setImageSets("SPDi01-130");

        setOriginalName("翠英　ベルヌーイ");
        setAltNames("スイエイベルヌーイ Suiei Berunuui");
        setDescription("jp",
                "@A @[アップ状態のルリグ１体をダウンする]@：次の対戦相手のターン終了時まで、このシグニのパワーを＋3000する。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Bernoulli, Verdant Wisdom");
        setDescription("en",
                "@A @[Down 1 of your upped LRIG]@: Until the end of your opponent's next turn, this SIGNI gets +3000 power." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "翠英 伯努利");
        setDescription("zh_simplified", 
                "@A 竖直状态的分身1只横置:直到下一个对战对手的回合结束时为止，这只精灵的力量+3000。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new DownCost(new TargetFilter().upped().anyLRIG()), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            gainPower(getCardIndex(), 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
