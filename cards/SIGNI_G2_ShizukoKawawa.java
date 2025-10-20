package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G2_ShizukoKawawa extends Card {

    public SIGNI_G2_ShizukoKawawa()
    {
        setImageSets("WXDi-CP02-088");

        setOriginalName("河和シズコ");
        setAltNames("カワワシズコ Kawawa Shizuko");
        setDescription("jp",
                "@A #D：あなたのデッキの一番上を公開する。そのカードが＜ブルアカ＞の場合、【エナチャージ１】をする。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Kawawa Shizuko");
        setDescription("en",
                "@A #D: Reveal the top card of your deck. If that card is <<Blue Archive>>, [[Ener Charge 1]].~{{C: This SIGNI gets +4000 power.@@" +
                "~#[[Ener Charge 1]]. The next time you would take damage from a LRIG this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Shizuko Kawawa");
        setDescription("en_fan",
                "@A #D: Reveal the top card of your deck. If it is a <<Blue Archive>> card, [[Ener Charge 1]]." +
               "~{{C: This SIGNI gets +4000 power.@@" +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "河和静子");
        setDescription("zh_simplified", 
                "@A 横置:你的牌组最上面公开。那张牌是<<ブルアカ>>的场合，[[能量填充1]]。\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new DownCost(), this::onActionEff);

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) || enerCharge(1).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
