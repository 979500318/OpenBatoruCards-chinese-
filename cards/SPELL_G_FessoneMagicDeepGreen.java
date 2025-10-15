package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
public final class SPELL_G_FessoneMagicDeepGreen extends Card {

    public SPELL_G_FessoneMagicDeepGreen()
    {
        setImageSets("WXDi-P14-TK04");

        setOriginalName("フェゾーネマジック・深緑");
        setAltNames("フェゾーネマジックシンリョク Fezoone Majikku Shinryoku");
        setDescription("jp",
                "((クラフトであるスペルは、使用後にゲームから除外される))\n\n" +
                "【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを場に出す。"
        );

        setName("en", "Fesonne Magic: Jade");
        setDescription("en",
                "((This spell can be used from your LRIG Deck during your main phase.))\n" +
                "((Spells that are Craft are removed from the game after they are used.))\n\n" +
                "[[Ener Charge 1]]. Then, put up to one target SIGNI from your Ener Zone onto your field."
        );
        
        setName("en_fan", "Fessone Magic - Deep Green");
        setDescription("en_fan",
                "((This craft is excluded from the game after use))\n\n" +
                "[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and put it onto the field."
        );

		setName("zh_simplified", "音乐节魔术·深绿");
        setDescription("zh_simplified", 
                "[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其出场。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);
        }

        private void onSpellEff()
        {
            enerCharge(1);

            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable()).get();
            putOnField(target);
        }
    }
}
