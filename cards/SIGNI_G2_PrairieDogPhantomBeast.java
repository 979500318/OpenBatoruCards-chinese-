package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_PrairieDogPhantomBeast extends Card {

    public SIGNI_G2_PrairieDogPhantomBeast()
    {
        setImageSets("WX24-P1-076");

        setOriginalName("幻獣　プレーリードッグ");
        setAltNames("ゲンジュウプレーリードッグ Genjuu Pureeriidoggu");
        setDescription("jp",
                "@E：あなたの他の＜地獣＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Prairie Dog, Phantom Beast");
        setDescription("en",
                "@E: Target 1 of your other <<Earth Beast>> SIGNI, and until the end of your opponent's next turn, it gets +3000 power." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "幻兽 草原犬鼠");
        setDescription("zh_simplified", 
                "@E :你的其他的<<地獣>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST).except(getCardIndex())).get();
            gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
